package com.example.demo.exception;

import com.example.demo.auth.enums.ErrorCode;
import com.example.demo.exception.dto.ErrorResponseDto;
import com.example.demo.security.exception.JwtAuthenticationException;
import com.example.demo.security.exception.RequiredJwtTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.demo.auth.enums.ErrorCode.*;
import static com.example.demo.auth.enums.ErrorCode.REQUIRED_TOKEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RequiredJwtTokenException.class)
    public ErrorResponseDto handleRequiredJwtTokenException() {
        return new ErrorResponseDto(REQUIRED_TOKEN);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ErrorResponseDto handleJwtAuthenticationException() {
        return new ErrorResponseDto(AUTHENTICATION_FAIL);
    }



}
