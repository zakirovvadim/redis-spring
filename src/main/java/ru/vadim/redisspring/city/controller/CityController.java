package ru.vadim.redisspring.city.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vadim.redisspring.city.dto.City;
import ru.vadim.redisspring.city.service.CityService;

@RestController
@RequestMapping("city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("{zipCode}")
    public Mono<City> getCity(@PathVariable String zipCode) {
        return cityService.getCity(zipCode);
    }
}
