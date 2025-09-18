package com.example.demo.exception.dto;

import com.example.demo.auth.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ErrorResponseDto {
    private int code;
    private String subCode;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDto(ErrorCode errorCode) {
        this.code = errorCode.getCode().value();
        this.subCode = errorCode.name();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }
}
