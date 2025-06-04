package com.noder.restapi.security;

import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtGenerator {
    private final SecretKey key;

    public JwtGenerator(@Value("${jwt.secret}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generate(Authentication authentication) {
        String username = authentication.getName();
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(86400);    // 1 day
        
        return Jwts.builder()
                .subject(username)
                .claim("roles", authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).toList())
                .issuedAt(java.util.Date.from(now))
                .expiration(java.util.Date.from(expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            System.out.println("Token validated");
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }
}
