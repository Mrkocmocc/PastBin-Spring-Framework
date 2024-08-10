package com.example.pastbin_spring_framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pastbin_spring_framework.entity.Past;

public interface PastRepository extends JpaRepository<Past, Long> {
    Past findByHashAndIsActiveTrue(String hash);
}
