package com.example.demo.user.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum RoleType {
    ADMIN,
    CUSTOMER,
    EMPLOYEE;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    public Collection<? extends GrantedAuthority> toAuthorities() {
        return List.of(new SimpleGrantedAuthority(getAuthority()));
    }
}
