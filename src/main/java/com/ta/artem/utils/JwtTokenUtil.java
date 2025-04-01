package com.ta.artem.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

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
        //System.out.println("version "+org.springframework.core.SpringVersion.getVersion());
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

    // Validate token
    public boolean isTokenValid(String token) {
        log.info("isTokenValid: " + token );
        try {
            // Parse the token with the secret key
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token) // This will throw exceptions if the token is invalid
                    .getBody();

            // Ensure the token is not expired
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (MalformedJwtException | SignatureException e) {
            System.out.println("Token is invalid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while validating token: " + e.getMessage());
        }
        return false;
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        log.info("getUsernameFromToken: " + token );

        JwtParser parser = Jwts.parser()
                .setSigningKey(SECRET_KEY) // Sign the parser with the secret key
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody(); // Parse the token claims
        return claims.getSubject(); // Return the 'sub' (subject), which is the username
    }


    /**
     * Extracts roles from the given JWT token and converts them to GrantedAuthority.
     *
     * @param token The JWT token from which to extract roles.
     * @return A list of GrantedAuthority representing the roles.
     */
    public List<GrantedAuthority> getRolesFromToken(String token) {
        log.info("getRolesFromToken: " + token);

        JwtParser parser = Jwts.parser()
                .setSigningKey(SECRET_KEY) // Sign the parser with the secret key
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody(); // Parse the token claims

        // Extract roles (assuming roles are stored as a claim named "role")
        String role = claims.get("role", String.class);
        return List.of(new SimpleGrantedAuthority(role)); // Convert to GrantedAuthority
    }
}

