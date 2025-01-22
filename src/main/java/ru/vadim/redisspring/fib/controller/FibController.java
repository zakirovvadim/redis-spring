package ru.vadim.redisspring.fib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vadim.redisspring.fib.service.FibService;

@RestController
@RequestMapping("fib")
public class FibController {

    @Autowired
    private FibService fibService;

    @GetMapping("{index}/{name}")
    public Mono<Integer> getFib(@PathVariable int index, @PathVariable String name) {
        return Mono.fromSupplier(() -> fibService.getFib(index, name));
    }
}
