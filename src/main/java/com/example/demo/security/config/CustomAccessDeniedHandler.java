package com.example.demo.security.config;

import com.example.demo.auth.enums.AuthErrorType;
import com.example.demo.security.exception.JwtErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.demo.security.exception.JwtErrorType.*;
import static jakarta.servlet.http.HttpServletResponse.*;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{

        String errorResponse = generateErrorResponse(ACCESS_DENIED);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(SC_FORBIDDEN);
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
