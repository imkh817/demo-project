package com.example.demo.exception.dto;

import com.example.demo.auth.enums.AuthErrorType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ErrorResponseDto {
    private int code;
    private String subCode;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDto(AuthErrorType authErrorType) {
        this.code = authErrorType.getCode().value();
        this.subCode = authErrorType.name();
        this.message = authErrorType.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
