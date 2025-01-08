package org.example.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String createJwt(long ttlMillis, Map<String, Object> claims) {
        Date expiration = new Date(System.currentTimeMillis() + ttlMillis);
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        // 在 claims 中设置 role（这里假设你是从用户对象中获取角色）
        if (claims.containsKey("userId")) {
            Long userId = (Long) claims.get("userId");
            // 假设你是通过用户 ID 来决定角色
            String role = (userId == 1) ? "ADMIN" : "USER";  // ID 为 1 的用户是管理员
            claims.put("role", role);  // 添加角色信息到 claims
        }
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseJwt(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseJwt(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateJwt(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
