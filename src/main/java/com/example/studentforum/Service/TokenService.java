package com.example.studentforum.Service;


import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    //   private static  final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String secretKey = "forumqwerwthrbgfgvrtevgbetergeytbtgvbtr1242352";
    private final long accessTokenExpiration = 7200;
    private final long refreshTokenExpiration = 86400;


    public String generateAccessToken(String id, int roleId) {
        Date expiration = new Date(System.currentTimeMillis() + accessTokenExpiration * 1000);

        return Jwts.builder()
                .setSubject(id)
                .claim("roleid", roleId)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String id, int roleId) {
        String refreshToken = Jwts.builder()
                .setSubject(id)
                .claim("roleid", roleId)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return refreshToken;
    }

    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            long expirationTime = claims.getExpiration().getTime();
            long currentTime = System.currentTimeMillis();

            return expirationTime < currentTime;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return true;
        }

    }

    public Claims getClaimFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public String getSubjectFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        String sub = claims.getSubject();
        return sub;
    }
    
}
