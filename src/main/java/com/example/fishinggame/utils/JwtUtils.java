package com.example.fishinggame.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secret key
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    public static String generateToken(String username, Integer userId, boolean isAdmin) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)  // Add userId to the token as a custom claim
                .claim("isAdmin", isAdmin) // Add isAdmin as a custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Extract userId from the token
    public static int extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)  // Update to use parserBuilder() to set the key and build the parser
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Integer.class);
    }

    // Extract isAdmin from the token
    public static boolean extractIsAdmin(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("isAdmin", Boolean.class);
    }


    public static boolean validateToken(String token, String username) {
        String tokenUsername = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username.equals(tokenUsername);
    }

    public static String validateTokenAndGetUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
