package com.example.hash_microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public long getHashCount(){
        return redisTemplate.opsForList().size("cached_hashes");
    }
}
