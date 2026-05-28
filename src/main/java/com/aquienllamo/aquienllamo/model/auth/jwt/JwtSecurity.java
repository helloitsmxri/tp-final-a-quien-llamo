package com.aquienllamo.aquienllamo.model.auth.jwt;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtSecurity {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;
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
        if (rawRoles == null) {
            return java.util.Collections.emptyList();
        }
        return rawRoles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    private <T> T extractClaim(String token, Function<Claims, T>
            claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token)
                && userDetails.isAccountNonLocked()
                && userDetails.isEnabled();
    }
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +
                        expiration))
                .signWith(getSignInKey())
                .compact();
    }
    private SecretKey getSignInKey() {
        byte[] keyBytes =
                this.jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    // jwtExpiration - Representa la duracion del token en milisegundos.
    //● jwtSecret - Es un String aleatorio, normalmente de una alta longitud, que se
    //utiliza para firmar y autenticar los tokens. Esta informacion es sensible ya que
    //permite generar tokens para nuestro sistema. Puede generarse en el siguiente
    //enlace: https://jwtsecret.com/generate
    //● extractUsername - Obtiene el nombre de usuario (subject) de un token JWT.
    //● generateToken - Crea un nuevo token JWT para un usuario, incluyendo sus roles
    //como "claims" adicionales.
    //● extractClaim - Es un método auxiliar que extrae una "claim" específica de un
    //token, aplicando una función resolutora.
    //● extractAllClaims - Es un método auxiliar que parsea el token JWT y devuelve
    //todas las "claims" (declaraciones) contenidas en su cuerpo.
    //● isTokenValid - Verifica si un token JWT es válido para un usuario dado,
    //comprobando el nombre de usuario, la caducidad, y el estado de la cuenta del
    //usuario.
    //● buildToken - Es un método auxiliar que construye y firma el token JWT, añadiendo
    //las "claims" extras, el sujeto, la fecha de emisión y la fecha de expiración.
    //● getSignInKey - Decodifica la clave secreta configurada (jwtSecretKey) y la
    //convierte en un objeto SecretKey para la firma y verificación de tokens.
    //● isTokenExpired - Comprueba si la fecha de expiración de un token JWT ya ha
    //pasado.
}
