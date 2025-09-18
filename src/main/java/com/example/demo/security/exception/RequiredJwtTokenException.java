package com.example.demo.security.exception;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Getter;

import static com.example.demo.auth.enums.ErrorCode.REQUIRED_TOKEN;

@Getter
public class RequiredJwtTokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public RequiredJwtTokenException() {
        super(REQUIRED_TOKEN.getMessage());
        errorCode = REQUIRED_TOKEN;
    }
}
