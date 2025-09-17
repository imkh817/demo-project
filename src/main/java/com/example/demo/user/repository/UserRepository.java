package com.example.demo.security.repository;

import com.example.demo.security.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {

    public Optional<UserEntity> findByUserId(String username);
}

// UserService -> UserRepository <- MyBatisRepository
// myBatis JPA
//
