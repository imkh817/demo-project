package com.example.demo.security.filter;

import com.example.demo.auth.enums.TokenType;
import com.example.demo.security.exception.JwtErrorType;
import com.example.demo.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 로그인 경로는 JWT 검증 제외
        if ("/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String token = extractJwtToken(request);

            if(isValidToken(token)){
                String username = tokenProvider.getUsernameFromJWT(token, TokenType.ACCESS);
                List<SimpleGrantedAuthority> authorities = tokenProvider.getAuthoritiesFromJWT(token, TokenType.ACCESS);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
             filterChain.doFilter(request,response);
        } catch (MalformedJwtException e) {
            handleJwtException(response, JwtErrorType.INVALID_TOKEN.name(), JwtErrorType.INVALID_TOKEN.getMessage());
        }catch (ExpiredJwtException e){
            handleJwtException(response, JwtErrorType.EXPIRED_TOKEN.name(), JwtErrorType.EXPIRED_TOKEN.getMessage());
        }
        catch (UnsupportedJwtException e) {
            handleJwtException(response, JwtErrorType.UNSUPPORTED_TOKEN.name(), JwtErrorType.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException e) {
            handleJwtException(response, JwtErrorType.ILLEGAL_TOKEN.name(), JwtErrorType.ILLEGAL_TOKEN.getMessage());
        } catch (SignatureException e) {
            handleJwtException(response, JwtErrorType.SIGNATURE_MISMATCH.name(), JwtErrorType.SIGNATURE_MISMATCH.getMessage());
        }
    }

    private String extractJwtToken(HttpServletRequest request){
        return Optional.of(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.length() >= 7 && token.substring(0,7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private boolean isValidToken(String token){
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token, TokenType.ACCESS)){
            return true;
        }
        return false;
    }

    private void handleJwtException(HttpServletResponse response, String errorCode, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
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
