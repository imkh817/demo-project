package com.example.demo.security.exception;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Getter;

import static com.example.demo.auth.enums.ErrorCode.EXPIRED_TOKEN;

@Getter
public class ExpiredJwtTokenException extends RuntimeException{

    private final ErrorCode errorCode;

    public ExpiredJwtTokenException() {
        super(EXPIRED_TOKEN.getMessage());
        this.errorCode = EXPIRED_TOKEN;
    }
}
