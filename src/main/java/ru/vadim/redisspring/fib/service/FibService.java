package ru.vadim.redisspring.fib.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    @Cacheable(value = "math:fib", key = "#index") // используй только аргумент индекс для расчета ключа в редисе
    public int getFib(int index, String name) {
        System.out.println("calculating fib for " + index + ", name : " + name);
        return this.fib(index);
    }


    //intetional 2^N
    private int fib(int index) {
        if (index < 2)
            return index;
        return fib(index - 1) + fib(index - 2);
    }
}
