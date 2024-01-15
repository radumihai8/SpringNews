package com.unibuc.newsapp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "asdasdas1231231das";
    private static final long EXPIRATION_TIME = 900000;

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static String extractUsername(String jwt) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(jwt)
                .getSubject();
    }

    public static void validateToken(String jwt) {
        JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(jwt);
    }
}
