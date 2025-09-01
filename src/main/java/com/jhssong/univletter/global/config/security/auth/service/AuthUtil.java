package com.jhssong.univletter.global.config.security.auth.service;

import com.jhssong.univletter.global.config.security.auth.exception.AuthExceptionUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public static String createAccessToken(String email, String key) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("type", "access");

        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getEmail(String token, String key) {
        try {
            Claims claims = extractClaims(token, key);
            return claims.get("email", String.class);
        } catch (Exception e) {
            throw AuthExceptionUtils.TokenInvalid();
        }

    }

    public static boolean isExpired(String token, String key) {
        try {
            Claims claims = extractClaims(token, key);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            throw AuthExceptionUtils.TokenExpired();
        } catch (Exception e) {
            throw AuthExceptionUtils.TokenInvalid();
        }
    }

    private static Claims extractClaims(String token, String key) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
