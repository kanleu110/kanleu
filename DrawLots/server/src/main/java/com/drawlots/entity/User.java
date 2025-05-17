package com.drawlots.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String avatar;

    @Column(unique = true)
    private String dingTalkId;

    @Column(unique = true)
    private String feishuPicId;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isVip;

    private LocalDateTime vipExpiryDate;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    public enum UserRole {
        USER,
        ADMIN
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
} 