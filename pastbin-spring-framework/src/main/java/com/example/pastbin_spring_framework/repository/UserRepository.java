package com.example.pastbin_spring_framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pastbin_spring_framework.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
