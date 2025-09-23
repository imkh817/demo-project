package com.example.demo.auth.repository;

import com.example.demo.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    Long insertUser(UserEntity user);

    UserEntity existsByUserId(String userId);

    Long join(UserEntity user);
}