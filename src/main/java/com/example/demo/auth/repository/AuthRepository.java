package com.example.demo.auth.repository;

import com.example.demo.user.entity.UserEntity;

public interface AuthRepository {
    public UserEntity saveUser(UserEntity user);

    UserEntity existsByUserId(String userId);

    Long join(UserEntity user);
}
