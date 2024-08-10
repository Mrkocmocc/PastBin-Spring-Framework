package com.example.pastbin_spring_framework.service;

import java.security.Principal;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.example.pastbin_spring_framework.dto.PastDto;
import com.example.pastbin_spring_framework.entity.Past;
import com.example.pastbin_spring_framework.mapping.PastMapper;
import com.example.pastbin_spring_framework.repository.PastRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PastService {
    
    private final PastRepository pastRepository;
    private final PastServiceClient pastServiceClient;
    private final UserService userService;

    public String savePast(String content, Timestamp expiration_time, Principal principal, Timestamp created_at) {
        Past past = new Past();
        String hash = pastServiceClient.getFreeHash();
        past.setContent(content);
        past.setHash(hash);
        past.setExpiration_time(expiration_time);
        past.setCreated_at(created_at);
        past.setActive(true);
        past.setUser(userService.findByUsername(principal.getName()));
        pastRepository.save(past);
        return hash;
    }

    public PastDto getPast(String hash){
        Past past = pastRepository.findByHashAndIsActiveTrue(hash);
        if (past == null) {
            return null;
        }
        return PastMapper.INSTANCE.toDto(past);
    }
}
