package com.example.hash_microservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hash_microservice.entity.HashPool;
import com.example.hash_microservice.repository.HashPoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashService {
    @Autowired
    private final HashPoolRepository hashPoolRepository;

    @Transactional
    public String getFreeHash() {
        HashPool hashPool = hashPoolRepository.findFirstByIsUsedFalse();
        if (hashPool != null) {
            return hashPool.getHash();
        }
        return null;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void generateHash() {
        long count = hashPoolRepository.countByIsUsedFalse();
        if (count < 1000) {
            for (int i = 0; i < 1000 - count; i++) {
                String hash = createHash();
                HashPool newHash = new HashPool();
                newHash.setHash(hash);
                newHash.setUsed(false);
                hashPoolRepository.save(newHash);
            }
        }
    }

    private String createHash() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String uuid = UUID.randomUUID().toString();
            byte[] hashBytes = md.digest(uuid.getBytes());
            String base64Hash = Base64.getEncoder().encodeToString(hashBytes);
            return base64Hash.replaceAll("[^A-Za-z0-9]", "").substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

}
