package com.example.eventbooking.security;

import com.example.eventbooking.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final SecretKey secretKey;

    // Read JWT secret from application.properties
    public JwtService(
            @Value("${jwt.secret}") String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    // =========================
    // GENERATE JWT TOKEN
    // =========================

    public String generateToken(
            String email,
            Role role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .signWith(secretKey)
                .compact();
    }


    // =========================
    // VALIDATE JWT TOKEN
    // =========================

    public boolean isTokenValid(
            String token) {

        try {

            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }


    // =========================
    // EXTRACT EMAIL
    // =========================

    public String extractEmail(
            String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    // =========================
    // EXTRACT ROLE
    // =========================

    public String extractRole(
            String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}