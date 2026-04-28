package com.pe.security.jwt;

import com.pe.model.entity.Usuario;
import com.pe.security.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Usuario usuario) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(usuario.getNombreUsuario())
                .claim("userId", usuario.getId())
                .claim("roles", usuario.getUsuarioRoles().stream()
                        .map(ur -> ur.getRol().getNombre())
                        .toList())
                .claim("type", "ACCESS")
                .id(UUID.randomUUID().toString())
                .issuer("salud-api")
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtProperties.expirationMs()))
                .signWith(getKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("TOKEN_EXPIRADO", e);
        } catch (JwtException e) {
            throw new JwtAuthenticationException("TOKEN_INVALIDO", e);
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
}