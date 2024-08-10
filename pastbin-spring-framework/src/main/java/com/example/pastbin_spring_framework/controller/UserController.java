package com.example.pastbin_spring_framework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pastbin_spring_framework.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {
        userService.createUser(username, password);
        return ResponseEntity.ok("User registered successfully");
    }

}
