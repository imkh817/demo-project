package com.example.demo.exception;

import com.example.demo.auth.enums.AuthErrorType;
import com.example.demo.exception.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@ExceptionHandler(BadCredentialsException.class)
    public ErrorResponseDto handleRequiredJwtTokenException() {
        return new ErrorResponseDto(AuthErrorType.BAD_CREDENTIAL);
    }

//@ResponseStatus(HttpStatus.UNAUTHORIZED)
//@ExceptionHandler(JwtAuthenticationException.class)
//    public ErrorResponseDto handleJwtAuthenticationException() {
//        return new ErrorResponseDto(AUTHENTICATION_FAILED);
//    }
}
