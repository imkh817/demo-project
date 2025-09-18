package com.example.demo.security.config;

import com.example.demo.security.dto.SecurityUserDetailsDto;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = repository.findByUserId(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자 id를 찾을 수 없습니다. id: " + username));
//
//        return SecurityUserDetailsDto.fromEntity(user);
//    }
//}
