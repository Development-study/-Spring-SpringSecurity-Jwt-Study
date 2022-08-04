package com.study.security.global.jwt;

import com.study.security.domain.user.enums.Role;
import com.study.security.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserService userService;

    @Value("{jwt.secret.key}")
    private String secretKey;

    private final Long tokenValidTime = 240 * 60 * 1000L;

    public String generateToken(String subject, Role role) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("security")
                .setSubject(subject)
                .claim("type", role)
                .claim("email", subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Transactional
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.getUserByEmail(getUserEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return getTokenBody(token).getSubject();
    }

    public Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = getTokenBody(jwtToken);

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
