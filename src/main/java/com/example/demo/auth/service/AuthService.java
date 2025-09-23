package com.example.demo.auth.service;

import com.example.demo.auth.dto.LoginRequestDto;
import com.example.demo.auth.dto.LoginResponseDto;
import com.example.demo.auth.dto.JoinRequestDto;
import com.example.demo.auth.dto.JoinResponseDto;
import com.example.demo.auth.repository.AuthRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.enums.RoleType;
import com.example.demo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getLoginId(), loginRequestDto.getPassword())
        );

        String accessToken = jwtTokenProvider.generateAccessToken(authenticate);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authenticate);
        return new LoginResponseDto(accessToken,refreshToken);
    }

    //등록
    //Join
    public JoinResponseDto join(JoinRequestDto joinRequestDto) {

        if(isDuplicatedId(joinRequestDto.getUserId())){
            throw new IllegalArgumentException("이미 존재하는 ID 입니다.");
        }

        // 회원가입
        UserEntity user = UserEntity.builder()
                .loginId(joinRequestDto.getUserId())
                .password(passwordEncoder.encode(joinRequestDto.getPassword()))
                .name(joinRequestDto.getName())
                .birthDate(joinRequestDto.getBirthDate())
                .roleType(RoleType.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .build();

        authRepository.join(user);
        return JoinResponseDto.fromEntity(user);
    }

    private boolean isDuplicatedId(String userId){
        return authRepository.existsByUserId(userId) != null;
    }
}
