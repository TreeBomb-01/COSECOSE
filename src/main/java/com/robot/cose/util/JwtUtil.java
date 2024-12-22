package com.robot.cose.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "9f8b682e2b1dcb6a57c87308b5d1fdb43e5f5a7a1cd4df2e89f1deed6c9a7e13\n"; //추후에 변경해야함

    // 토큰 유효시간 (1시간)
    private static final long EXPIRATION_TIME = 3600000;

    // JWT 생성
    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // 사용자 식별자
                .setIssuedAt(new Date()) // 토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 알고리즘 및 비밀 키
                .compact();
    }

    // JWT 검증 및 클레임 추출
    public static String validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return claims.getBody().getSubject(); // 사용자 식별자 반환
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid JWT token");
        }
    }
}

