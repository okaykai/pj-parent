package cn.edu.xcu.manager.utils;

import cn.edu.xcu.yky.model.entity.system.SysUser;
import cn.hutool.core.lang.Console;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * @description: JWT工具类
 * @author: Keith
 */

@Component
public class JwtUtil {


    @Value("${secret}")
    private String secret;

    @Value("${expiration}")
    private Long expiration;

    private Key key;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Console.log("JWT key initialized");
    }

    public String generateToken(SysUser sysUser) {

        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(sysUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Jwts.builder()
                .claim("user", userJson)
                .setSubject(sysUser.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            Console.log("Token validated successfully");
            return true;
        } catch (SignatureException e) {
            Console.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            Console.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            Console.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            Console.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            Console.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public SysUser parseToken(String token) throws JsonProcessingException {

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        String userJson = claims.get("user", String.class);

        return objectMapper.readValue(userJson, SysUser.class);
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}