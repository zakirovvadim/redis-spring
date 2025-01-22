package ru.vadim.redisspring.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vadim.redisspring.weather.service.WeatherService;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("{zip}")
    public Mono<Integer> getWeather(@PathVariable int zip) {
        return Mono.fromSupplier(() -> this.weatherService.getInfo(zip));
    }
}
