package uz.pdp.audiobook.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.pdp.audiobook.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expirationDate}")
    private Integer expirationDate;

    private final static int ONE_DAY_IN_MILLISECONDS = 86_400_000;

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .signWith(key)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate * ONE_DAY_IN_MILLISECONDS))
                .compact();
    }

    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}