package com.example.demo.user.repository;

import com.example.demo.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MyBatisUserRepository implements UserRepository {
    @Override
    public Optional<UserEntity> findByUserId(String username) {
        return null;
    }
}
