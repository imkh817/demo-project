package com.example.demo.auth.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,  "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,  "지원되지 않는 JWT 토큰입니다."),
    EMPTY_CLAIMS(HttpStatus.UNAUTHORIZED, "JWT 클레임이 비어있습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "JWT 토큰 검증 실패"),
    REQUIRED_TOKEN(HttpStatus.UNAUTHORIZED,"JWT 토큰이 필요합니다");

    private final HttpStatus code;
    private final String message;

    ErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

}
