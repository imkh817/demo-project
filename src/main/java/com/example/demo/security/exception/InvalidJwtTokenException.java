package com.example.demo.security.exception;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Getter;

import static com.example.demo.auth.enums.ErrorCode.INVALID_TOKEN;

@Getter
public class InvalidJwtTokenException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidJwtTokenException() {
        super(INVALID_TOKEN.getMessage());
        this.errorCode = INVALID_TOKEN;
    }
}
