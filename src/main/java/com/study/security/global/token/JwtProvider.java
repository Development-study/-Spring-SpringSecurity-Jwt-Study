package com.study.security.global.token;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long id) {
        return generateToken(id.toString(), jwtProperties.getSecretKey(), jwtProperties.getAccessExp());
    }

    public Long getTokenSubject(String token) {
        return Long.parseLong(parseToken(token).getSubject());
    }

    public String generateToken(String id, String key, Long exp) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("security")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(exp).toMillis()))
                .claim("id", id)
                .claim("key", key)
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims parseToken(String authorizationHeader) {
        String token = validationAuthorizationHeader(authorizationHeader);

        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private String validationAuthorizationHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }
        return null;
    }

}
