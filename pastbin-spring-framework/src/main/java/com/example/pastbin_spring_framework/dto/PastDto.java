package com.example.pastbin_spring_framework.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PastDto {
    private Long id;
    private String content;
    @JsonProperty(required = false)
    private String hash;
    private Timestamp expiration_time;
    @JsonProperty(required = false)
    private Timestamp created_at;
    @JsonProperty(required = false)
    private boolean isActive;
    @JsonProperty(required = false)
    private UserDto user;
}
