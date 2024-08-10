package com.example.pastbin_spring_framework.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "pastes")
@RequiredArgsConstructor
public class Past {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "hash")
    private String hash;

    @Column(name = "expiration_time", columnDefinition = "timestamp without time zone")
    private Timestamp expiration_time;

    @Column(name = "created_at", columnDefinition = "timestamp without time zone")
    private Timestamp created_at;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;

}
