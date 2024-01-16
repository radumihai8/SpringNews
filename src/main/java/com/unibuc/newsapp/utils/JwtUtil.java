package com.unibuc.newsapp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import com.auth0.jwt.interfaces.DecodedJWT;

    public class JwtUtil {

        private static final String SECRET_KEY = "asdasdas1231231das";
        private static final long EXPIRATION_TIME = 900000; // Adjust as needed

        public static String generateToken(String username, String role) {
            // prefix role with "ROLE_" for Spring Security
            role = "ROLE_" + role;
            return JWT.create()
                    .withSubject(username)
                    .withClaim("role", role)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(SECRET_KEY));
        }

        public static String extractUsername(String jwt) {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(jwt)
                    .getSubject();
        }

        public static String extractRole(String jwt) {
            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(jwt);
            return decodedJwt.getClaim("role").asString();
        }

        public static boolean validateToken(String jwt) {
            try {
                JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(jwt);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }


