package com.fitcom.fitcom_restapi.security.jwt;

import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

import static com.fitcom.fitcom_restapi.security.utils.SecurityConstants.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value(JWT_SECRET)
    public String jwtSecret;

    @Value(ACCESS_TOKEN_EXPIRATION_TIME)
    public int jwtExpirationMs;

    @Value(ACCESS_TOKEN_NAME)
    public String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest req) {
        Cookie cookie = WebUtils.getCookie(req, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        return ResponseCookie.from(jwtCookie, jwt)
                .path(API_ENDPOINT)
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, "")
                .path(API_ENDPOINT)
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e; // Re-throw the exception
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
