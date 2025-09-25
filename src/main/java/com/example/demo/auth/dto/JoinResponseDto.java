package com.example.demo.auth.dto;

import com.example.demo.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class JoinResponseDto {
    private Long id;

    public static JoinResponseDto fromEntity(UserEntity user){
        return JoinResponseDto.builder()
                .id(user.getId())
                .build();
    }
}
