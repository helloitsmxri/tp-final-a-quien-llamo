package com.aquienllamo.aquienllamo.model.auth.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
//es el servicio que crea y valida los tokens JWT.

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return buildToken(claims, userDetails, jwtExpiration);
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<?> rawRoles = claims.get("roles", List.class);
        if (rawRoles == null) return Collections.emptyList();
        return rawRoles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token)
                && userDetails.isAccountNonLocked()
                && userDetails.isEnabled();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = this.jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return buildToken(claims, userDetails, refreshTokenExpiration);
    }

    public boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        try {
            final String username = extractUsername(refreshToken);
            return (username.equals(userDetails.getUsername()))
                    && !isTokenExpired(refreshToken);
        } catch (JwtException e) {
            return false;
        }
    }

    public String generatePasswordResetToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("type", "password-reset");

        return buildToken(claims, userDetails, 1000*60*15); // esto de acá a la derecha es la expiración equivale
                                                                    // como a quince min
    }

    // validación
    public boolean validatePasswordResetToken(String token, UserDetails userDetails){
        try{
            Claims claims = extractAllClaims(token);
            String tipo = claims.get("type", String.class);
            if (!"password-reset".equals(tipo)){
                return false;
            }
            return validateRefreshToken(token,userDetails);
        }catch (JwtException e){ // revisar si este tipo de excepción es la idónea o la EXCEPTION COMÚN.
            return false;
        }
    }
}