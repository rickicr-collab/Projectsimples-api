package com.RicardoRosendo.ProjectSimples.Security;

import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generationToken(String username) {
        SecretKey key = getSecretBykey();
        return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(key).compact();
    }

    private SecretKey getSecretBykey() {
        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
        return key;

    }

    public boolean isValidToken(String token) {
        Claims clains = getClaims(token);
        if (Objects.nonNull(clains)) {
            String username = clains.getSubject();
            Date expirationDate = clains.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if (Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUserName(String token){
        Claims claims = getClaims(token);
        if(Objects.nonNull(claims)){
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        SecretKey key = getSecretBykey();
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
