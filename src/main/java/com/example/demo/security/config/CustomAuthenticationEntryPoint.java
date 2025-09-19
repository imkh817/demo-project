package com.example.demo.security.config;

import com.example.demo.auth.enums.AuthErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        AuthErrorType errorType = (AuthErrorType) request.getAttribute("authErrorType");

        if(errorType == null){
            errorType = AuthErrorType.AUTHENTICATION_FAILED;
        }

        String errorResponse = generateErrorResponse(errorType);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(errorResponse);
    }

    private String generateErrorResponse(AuthErrorType error){
        String response = String.format("""
                        {
                            "success": false,
                            "error": "UNAUTHORIZED",
                            "message": "%s",
                            "timestamp": "%s"
                        }
                        """,
                error.getMessage(),
                LocalDateTime.now()
        );
        return response;
    }
}
