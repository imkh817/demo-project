package com.example.demo.security.repository;

import com.example.demo.security.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MyBatisUserRepository implements UserRepository {
    @Override
    public Optional<UserEntity> findByUserId(String username) {
        return null;
    }
}
