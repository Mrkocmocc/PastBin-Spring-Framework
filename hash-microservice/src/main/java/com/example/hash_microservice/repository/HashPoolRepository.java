package com.example.hash_microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hash_microservice.entity.HashPool;

public interface HashPoolRepository extends JpaRepository<HashPool, Long> {
    long countByIsUsedFalse();
    HashPool findTopByIsUsedFalse();
    @Query("select h from HashPool h where h.isUsed = false order by h.id asc limit 1")
    HashPool findFirstByIsUsedFalse();

    @Query("select h from HashPool h where h.isUsed = false order by h.id asc limit 100")
    List<HashPool> findTop100ByIsUsedFalse();
}
