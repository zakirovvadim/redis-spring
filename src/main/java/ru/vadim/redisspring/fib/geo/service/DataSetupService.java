package ru.vadim.redisspring.fib.geo.service;

import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.vadim.redisspring.fib.geo.dto.GeoLocation;
import ru.vadim.redisspring.fib.geo.dto.Restaurant;
import ru.vadim.redisspring.fib.geo.util.RestaurantUtil;

@Service
public class DataSetupService implements CommandLineRunner {

    private RGeoReactive<Restaurant> geo;
    private RMapReactive<String, GeoLocation> map;
    @Autowired
    private RedissonReactiveClient client;

    @Override
    public void run(String... args) throws Exception {
        this.geo =  this.client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
        this.map = this.client.getMap("us", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
        Flux.fromIterable(RestaurantUtil.getRestaurants())
                .flatMap(r -> this.geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
                .flatMap(r -> this.map.fastPut(r.getZip(), new GeoLocation(r.getLongitude(), r.getLatitude())))
                .doFinally(s -> System.out.println("restaurants added " + s))
                .subscribe();
    }
}
