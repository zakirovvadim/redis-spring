package ru.vadim.redisspring.city.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.vadim.redisspring.city.dto.City;

@Service
public class CityClient {
    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> getCity(final String zipCode) {
        return this.webClient
                .get()
                .uri("{zipcode}",  zipCode)
                .retrieve()
                .bodyToMono(City.class);
    }

    public Flux<City> getAll() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(City.class);
    }
}
