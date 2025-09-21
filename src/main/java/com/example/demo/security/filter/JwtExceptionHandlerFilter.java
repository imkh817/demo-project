package com.example.demo.security.filter;

import com.example.demo.security.exception.JwtErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.demo.security.exception.JwtErrorType.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            log.info("JwtExceptionHandlerFilter 실행");
            filterChain.doFilter(request,response);
        } catch (MalformedJwtException e) {
            log.info("올바르지 않은 JWT 토큰 형식입니다.");
            handleJwtException(response, INVALID_TOKEN);
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다.");
            handleJwtException(response, EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 형식입니다.");
            handleJwtException(response, UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 비어있거나 잘못되었습니다.");
            handleJwtException(response, ILLEGAL_TOKEN);
        } catch (SignatureException e) {
            log.info("JWT 서명이 유효하지 않습니다.");
            handleJwtException(response, SIGNATURE_MISMATCH);
        }
    }

    private void handleJwtException(HttpServletResponse response, JwtErrorType errorType) throws IOException {
        String errorCode = errorType.name();
        String message = errorType.getMessage();
        response.setStatus(UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format(
                """
                        {
                            "success": false,
                            "error": "%s",
                            "message": "%s",
                            "timestamp": "%s"
                        }
                        """, errorCode, message, LocalDateTime.now()));
    }

}
