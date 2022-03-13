package com.fede.alk.back.app.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(new Date().getTime() + expiration * 1000L))
                .sign(Algorithm.HMAC512(secret));
    }


    public String obetenerNombre(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.decode(token).getSignature();
            return true;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
