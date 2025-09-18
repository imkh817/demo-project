package com.example.demo.security.config;

import com.example.demo.auth.enums.AuthErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{

        AuthErrorType errorType = (AuthErrorType) request.getAttribute("authErrorType");

        if(errorType == null){
            errorType = AuthErrorType.AUTHENTICATION_FAILED;
        }

        String errorResponse = generateErrorResponse(errorType);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(errorResponse);
    }

    private String generateErrorResponse(AuthErrorType error){
        String response = String.format("""
                        {
                            "success": false,
                            "error": "FORBIDDEN",
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
