package com.example.demo.exception;

import com.example.demo.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   // @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        String unsupportedMethod = ex.getMethod();
        Set<String> supportedHttpMethods = ex.getSupportedHttpMethods()
                .stream()
                .map(HttpMethod::name)
                .collect(Collectors.toSet());

        String message = String.format("%s 요청: [%s], 허용: %s",
                errorCode.getDefaultMessage(),
                unsupportedMethod,
                supportedHttpMethods);

        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.name())
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }
//@ResponseStatus(HttpStatus.UNAUTHORIZED)
//@ExceptionHandler(JwtAuthenticationException.class)
//    public ErrorResponseDto handleJwtAuthenticationException() {
//        return new ErrorResponseDto(AUTHENTICATION_FAILED);
//    }
}
