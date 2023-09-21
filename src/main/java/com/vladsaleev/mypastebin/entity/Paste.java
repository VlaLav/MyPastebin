package com.vladsaleev.mypastebin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paste")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicStatus status;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private long expiredTime;

    @Column(nullable = false, updatable = false)
    private String hash;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
