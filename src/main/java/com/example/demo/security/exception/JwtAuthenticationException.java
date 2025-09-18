package com.example.demo.security.exception;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Getter;

import static com.example.demo.auth.enums.ErrorCode.AUTHENTICATION_FAIL;

@Getter
public class JwtAuthenticationException extends RuntimeException{
    private final ErrorCode errorCode;

    public JwtAuthenticationException() {
        super(AUTHENTICATION_FAIL.getMessage());
        this.errorCode = AUTHENTICATION_FAIL;
    }

}
