package ru.vadim.redisspring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    // have a strategy cache evict
    @Cacheable(value = "math:fib", key = "#index") // используй только аргумент индекс для расчета ключа в редисе
    public int getFib(int index) {
        System.out.println("calculating fib for " + index);
        return this.fib(index);
    }

    // PUT / POST/ PATCH/DELETE
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("clearing cache key");
    }


    //intetional 2^N
    private int fib(int index) {
        if (index < 2)
            return index;
        return fib(index - 1) + fib(index - 2);
    }
}
