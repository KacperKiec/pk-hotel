package edu.zespol5.pkhotelbackend.config;

import edu.zespol5.pkhotelbackend.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String secretKey = "abcdefghijklmnopqrstuvwxyabcdefghijklmnopqrstuvwxy";
    private final long jwtExpirationDate = 60 * 60 * 1000;

    public String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}

