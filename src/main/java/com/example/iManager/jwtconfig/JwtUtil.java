package com.example.iManager.jwtconfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String secretKey = "iManagerUserSecurity_SecretKey123456";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Generate jwt token
    public String generateToken(String username){
        // 24hr in MileSeconds
        long expirationTime = 86400000;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate jwt token
    public boolean validateToken(String token,String expectedUsername){
        try {
            System.out.println("validateToken method mei agyaa");
            String username = extractUsername(token);
            System.out.println("username extract hua");
            return username.equals(expectedUsername) && !isTokenExpired(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    // Check token expiry
    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
