package com.example.demo.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtTokenProvider {

    private SecretKey key;
    private long jwtExpirationInMs;

    // JWT 토큰 생성
    public String generateAccessToken(Authentication authentication){
        String name = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date issueDate = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject(name)
                .claim("authorities", authorities)
                .setIssuedAt(issueDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // JWT 토큰에서 사용자명 추출
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // JWT 토큰에서 권한 추출
    public List<SimpleGrantedAuthority> getAuthoritiesFromJWT(String token){
        String authorities = Jwts.parserBuilder()
                .setSigningKey(key)
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
    public boolean validateToken(String authToken){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex) {
            log.error("잘못된 JWT 토큰");
        } catch (ExpiredJwtException ex) {
            log.error("만료된 JWT 토큰");
        } catch (UnsupportedJwtException ex) {
            log.error("지원되지 않는 JWT 토큰");
        } catch (IllegalArgumentException ex) {
            log.error("JWT 클레임이 비어있음");
        } catch (JwtException ex) {
            log.error("JWT 토큰 검증 실패");
        }
        return false;
    }


}
