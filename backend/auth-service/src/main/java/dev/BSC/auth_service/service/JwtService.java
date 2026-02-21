package dev.BSC.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtService - Servicio de autenticación JWT
 * 
 * Responsabilidades:
 * - Generar tokens JWT para usuarios autenticados
 * - Validar tokens JWT recibidos
 * - Extraer información del token (username, rol, etc.)
 * 
 * El token incluye:
 * - username: Identificador único del usuario
 * - role: Rol del usuario (ADMIN, USER, SUPERVISOR)
 * - Fecha de expiración (12 horas por defecto)
 */
@Service
public class JwtService {

    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String jwtSecret;

    @Value("${jwt.expiration:43200000}") // 12 horas en milisegundos
    private long jwtExpiration;

    /**
     * Genera un token JWT con el username y rol del usuario
     * @param username Usuario autenticado
     * @param role Rol del usuario
     * @return Token JWT firmado y codificado en Base64
     */
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        
        return createToken(claims, username);
    }

    /**
     * Crea el token JWT firmándolo con la clave secreta
     * @param claims Mapa de claims (datos adicionales)
     * @param subject Usuario
     * @return Token JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el username del token JWT
     * @param token Token JWT
     * @return Usuario extraído del token
     */
    public String extractUsername(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Extrae el rol del usuario del token JWT
     * @param token Token JWT
     * @return Rol del usuario
     */
    public String extractRole(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (String) claims.get("role");
    }

    /**
     * Valida si el token JWT es válido
     * @param token Token JWT a validar
     * @return true si es válido, false si no
     */
    public boolean isTokenValid(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
