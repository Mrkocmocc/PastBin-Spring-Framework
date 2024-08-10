package com.example.hash_microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hash_microservice.service.HashService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hash")
@RequiredArgsConstructor
public class HashController {

    private final HashService hashService;

    @GetMapping("/free")
    public ResponseEntity<String> getHash() {
        String hash = hashService.getFreeHash();
        if (hash != null) {
            return ResponseEntity.ok(hash);
        } else
            return ResponseEntity.status(503).body("No free hash found");
    }

}
