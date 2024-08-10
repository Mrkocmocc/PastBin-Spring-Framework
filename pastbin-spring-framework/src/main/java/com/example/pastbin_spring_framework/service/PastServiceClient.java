package com.example.pastbin_spring_framework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PastServiceClient {
    private final RestTemplate restTemplate;

    public String getFreeHash() {
        String url = "http://localhost:8081/hash/free";
        return restTemplate.getForObject(url, String.class);
    }

}
