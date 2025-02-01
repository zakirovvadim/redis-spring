package ru.vadim.redisspring.fib.geo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.vadim.redisspring.fib.geo.dto.Restaurant;
import ru.vadim.redisspring.fib.geo.service.RestaurantLocatorService;

@RestController
@RequestMapping("geo")
public class RestorantController {

    @Autowired
    private RestaurantLocatorService locatorService;

    @GetMapping("{zip}")
    public Flux<Restaurant> getRestaurants(@PathVariable String zip) {
        return locatorService.getRestaurants(zip);
    }
}
