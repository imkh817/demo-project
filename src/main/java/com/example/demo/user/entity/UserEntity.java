package com.example.demo.user.entity;

import com.example.demo.user.enums.RoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserEntity {
    private long id;
    private String loginId;
    private String password;
    private boolean accountNonLocked  = true;
    private RoleType roleType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    // 탈퇴 여부
    private boolean deleted = false;
    private LocalDateTime deletedAt;
}
