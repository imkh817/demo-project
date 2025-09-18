package com.example.demo.security.jwt;

import com.example.demo.auth.enums.TokenType;
import com.example.demo.security.exception.ExpiredJwtTokenException;
import com.example.demo.security.exception.InvalidJwtTokenException;
import com.example.demo.security.exception.JwtAuthenticationException;
import com.example.demo.security.exception.UnsupportedJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.auth.enums.ErrorCode.*;

@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.accessKey}")
    private String accessKey;
    @Value("${jwt.refreshKey}")
    private String refreshKey;
    @Value("${jwt.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    // JWT 토큰 생성
    public String generateAccessToken(Authentication authentication){
        String name = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date issueDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(issueDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(name)
                .claim("authorities", authorities)
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(TokenType.ACCESS))
                .compact();
    }

    // JWT 토큰 생성
    public String generateRefreshToken(Authentication authentication){
        String name = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date issueDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(issueDate.getTime() + jwtExpirationInMs * 7); // 리프레시 토큰은 7배 더 길게

        return Jwts.builder()
                .setSubject(name)
                .claim("authorities", authorities)
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(TokenType.REFRESH))
                .compact();
    }

    // JWT 토큰에서 사용자명 추출
    public String getUsernameFromJWT(String token, TokenType tokenType) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // JWT 토큰에서 권한 추출
    public List<SimpleGrantedAuthority> getAuthoritiesFromJWT(String token, TokenType tokenType) {
        String authorities = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("authorities", String.class);

        return Arrays
                .stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String authToken, TokenType tokenType){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(tokenType))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex) {
            throw new InvalidJwtTokenException();
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtTokenException();
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException();
        } catch (IllegalArgumentException ex) {
            throw new InvalidJwtTokenException();
        } catch (JwtException ex) {
            throw new JwtAuthenticationException();
        }
    }

    private SecretKey getSigningKey(TokenType tokenType) {
        String secret = (tokenType == TokenType.ACCESS) ? accessKey : refreshKey;
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


}
