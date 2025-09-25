package com.example.demo.security.config;

import com.example.demo.auth.enums.AuthErrorType;
import com.example.demo.security.exception.JwtErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.demo.security.exception.JwtErrorType.AUTHENTICATION_FAILED;
import static jakarta.servlet.http.HttpServletResponse.*;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String errorResponse = generateErrorResponse(AUTHENTICATION_FAILED);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(errorResponse);
    }

    private String generateErrorResponse(JwtErrorType error){
        String response = String.format("""
                        {
                            "success": false,
                            "error": "%s",
                            "message": "%s",
                            "timestamp": "%s"
                        }
                        """,
                error.name(),
                error.getMessage(),
                LocalDateTime.now()
        );
        return response;
    }
}
