package com.example.demo.auth.repository;

import com.example.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisAuthRepository implements AuthRepository{

    private final AuthMapper authMapper;

    @Override
    public UserEntity saveUser(UserEntity user) {
        authMapper.insertUser(user);
        return user;
    }

    @Override
    public UserEntity existsByUserId(String userId) {
        return authMapper.existsByUserId(userId);
    }

    @Override
    public Long join(UserEntity user) {
        return authMapper.join(user);
    }
}
