package com.ta.artem.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey SECRET_KEY;

    // Expiration time in milliseconds (e.g., 1 hour)
    private static final long EXPIRATION_TIME = 3600000;

    /**
     * Initialize the SECRET_KEY after the bean is created.
     */
    @PostConstruct
    public void init() {
        // Decode the secret and generate the key
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Generates a JWT token.
     *
     * @param claims  Data to include in the payload (e.g., username, roles)
     * @param subject Token subject (e.g., the username)
     * @return Generated JWT token
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        Date now = Date.from(Instant.now());
        Date expiration = Date.from(Instant.now().plusMillis(EXPIRATION_TIME));

        return Jwts.builder()
                .claims(claims) // Add claims
                .subject(subject) // Add subject
                .issuedAt(now) // Token issue date
                .expiration(expiration) // Token expiration
                .signWith(SECRET_KEY) // Use signWith with the new API
                .compact(); // Build the token

    }
}

