package ru.vadim.redisspring.city.service;

import org.redisson.api.*;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.vadim.redisspring.city.client.CityClient;
import ru.vadim.redisspring.city.dto.City;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private RMapReactive<String, City> cityMap;

    public CityService(RedissonReactiveClient client) {
        this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

//    /*
//    get from cache
//    if empty - get from db/source
//                    put it in cache
//    return
//     */
//
//    public Mono<City> getCity(final String zipCode) {
//        return this.cityMap.get(zipCode) // получаем из редис кеша
//                .switchIfEmpty(this.cityClient.getCity(zipCode) // если пусто, обращаемся к клиенту для получения извне
//                        .flatMap(c -> this.cityMap.fastPut(zipCode, c, 10, TimeUnit.SECONDS).thenReturn(c)) // просто кладем под ключ зипКода новый сити, и так как фастПут возвращает булеан, а нам надо сити, просто вызываем thenReturn этого же сити
//                );
//    }


    // делаем запрос в обход внешнего сервиса, сразу в редис, а сам редис периодически загружает данные
    public Mono<City> getCity(String zipCode) {
        return cityMap.get(zipCode)
                .onErrorResume(ex -> this.cityClient.getCity(zipCode));
    }

    @Scheduled(fixedRate = 10_000)
    public void updateCity() {
        this.cityClient.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }
}
