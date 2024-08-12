package com.example.hash_microservice.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private final StringRedisTemplate redisTemplate;
    
    @Autowired
    private final RedisService redisService;

    private static final String HASH_CACHE_KEY = "cached_hashes";

    @Transactional
    public String getFreeHash() {
        String hash = redisTemplate.opsForList().rightPop(HASH_CACHE_KEY);
        if (hash != null) {
            return hash;
        }

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

        cacheHashes();
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

    private void cacheHashes() {
        if (redisService.getHashCount() < 100) {
            List<HashPool> hashes = hashPoolRepository.findTop100ByIsUsedFalse();
            for (int i = 0; i < hashes.size() - redisService.getHashCount(); i++) {
                redisTemplate.opsForList().rightPush(HASH_CACHE_KEY, hashes.get(i).getHash());
                hashes.get(i).setUsed(true);
                hashPoolRepository.save(hashes.get(i));
            }
        }
    }

}
