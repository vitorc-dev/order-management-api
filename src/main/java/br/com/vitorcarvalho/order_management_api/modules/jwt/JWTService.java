package br.com.vitorcarvalho.order_management_api.modules.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public String generateToken(String email){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.expirationMs);

        return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(this.getSigningKey())
            .compact();
    }

    public String extractEmail(String token){
        return this.parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String email){
        String extractedEmail = this.extractEmail(token);
        return extractedEmail.equals(email) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return this.parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
            .verifyWith(this.getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }
}
