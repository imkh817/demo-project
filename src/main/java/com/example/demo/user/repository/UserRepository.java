package com.example.demo.user.repository;

import com.example.demo.user.entity.UserEntity;

import java.util.Optional;

// UserService -> UserRepository <- MyBatisRepository, JpaRepository
public interface UserRepository {
    public Optional<UserEntity> findByUserId(String username);
}


