package com.freshermanage.config.jwt;

import com.freshermanage.config.security.CustomUserDetails;
import io.jsonwebtoken.*;
import jdk.jpackage.internal.Log;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private String JWT_SECRET = "FresherManage";
    private int JWT_EXPIRATION = 86400000;

    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            Log.error("Invalid JWT token");
        }
        return false;
    }
}
