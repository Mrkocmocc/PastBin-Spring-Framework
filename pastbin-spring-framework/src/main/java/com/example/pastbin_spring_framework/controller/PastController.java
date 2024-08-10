package com.example.pastbin_spring_framework.controller;

import java.security.Principal;
import java.sql.Timestamp;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pastbin_spring_framework.dto.PastDto;
import com.example.pastbin_spring_framework.service.PastService;

@Controller
@RequiredArgsConstructor
public class PastController {

    private final PastService pastService;

    @PostMapping("/past/save")
    public ResponseEntity<String> postMethodName(@RequestBody PastDto pastDto, Principal principal) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp expirationTime = new Timestamp(currentTime.getTime() + pastDto.getExpiration_time().getTime());
        String content = pastDto.getContent();
        String hash = pastService.savePast(content, expirationTime, principal, currentTime);
        return ResponseEntity.ok("Past registered successfully with expiration time: " + expirationTime + " and hash: " + hash);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<String> getPast(@PathVariable String hash) {
        PastDto pastDto = pastService.getPast(hash);
        if (pastDto == null) {
            return ResponseEntity.ok("No past found with hash: " + hash);
        }
        return ResponseEntity.ok(pastDto.getContent());
    }
    
}
