package com.prueba.spring.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 1000 * 60 * 3; // 3 minutos

    public String createToken(Authentication authentication) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            String email = authentication.getPrincipal().toString();
            String authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            String jwtToken = JWT.create()
                    .withIssuer(this.userGenerator)
                    .withSubject(email)
                    .withClaim("authorities", authorities)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);
            return jwtToken;
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error al generar el token", null);
        }
    }

    public DecodedJWT validateToken(String token) {
        try {
            DecodedJWT decodedJWT;
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            decodedJWT = jwtVerifier.verify(token);
            return decodedJWT;
        } catch (Exception e) {
            throw new JWTVerificationException("Token invalido, no autorizado");
        }
    }

    public String extracEmail(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaim(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
