package com.example.demo.security.dto;

import com.example.demo.security.entity.UserEntity;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
public class SecurityUserDetailsDto implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public static SecurityUserDetailsDto fromEntity(UserEntity entity) {
        return SecurityUserDetailsDto.builder()
                .username(entity.getLoginId())
                .password(entity.getPassword())
                .authorities(entity.getRoleType().toAuthorities())
                .build();
    }

}
