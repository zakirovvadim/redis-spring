package ru.vadim.redisspring.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

// Важно, ограничения, кеширования чероез аннтоцаии не поддерживает паблишеры моно и флакс, так как там мы строим пайплаин, а не возвращаем готовый объект
// не поддерживает TTL
// нельзя менять типы
@Service
public class WeatherService {

    @Autowired
    private ExternalServiceClient client;

    // consumers always get info from cache redis
    @Cacheable("weather")
    public int getInfo(int zip) {
        return 0;
    }

    // but update data abouyt weather occures in scheduled
    @Scheduled(fixedRate = 10_000)
    public void update() {
        System.out.println("updating weather");
        IntStream.rangeClosed(1, 5)
                .forEach(this.client::getWeatherInfo);
    }
}
