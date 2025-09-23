package com.example.demo.user.entity;

import com.example.demo.auth.dto.JoinRequestDto;
import com.example.demo.auth.dto.JoinResponseDto;
import com.example.demo.user.enums.RoleType;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Builder
@Getter
@Alias("User")
public class UserEntity {
    private long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime birthDate;

    private RoleType roleType;
    private boolean accountNonLocked  = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private boolean deleted = false;
    private LocalDateTime deletedAt;
}
