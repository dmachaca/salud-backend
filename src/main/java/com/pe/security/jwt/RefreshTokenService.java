package com.pe.security.service;

import com.pe.model.entity.RefreshToken;
import com.pe.model.entity.Usuario;
import com.pe.repository.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final PasswordEncoder encoder;

    public String create(Usuario usuario) {

        String rawToken = generateToken(); // secreto
        String jti = generateToken();      // identificador público

        String hash = encoder.encode(rawToken);

        RefreshToken entity = RefreshToken.builder()
                .usuario(usuario)
                .tokenHash(hash)
                .jti(jti)
                .expiracion(OffsetDateTime.now().plusDays(7))
                .revocado(false)
                .build();

        repository.save(entity);

        return rawToken + "." + jti;
    }

    public RefreshToken validate(String fullToken) {

        String[] parts = fullToken.split("\\.");

        if (parts.length != 2) {
            throw new RuntimeException("TOKEN MAL FORMADO");
        }

        String rawToken = parts[0];
        String jti = parts[1];

        RefreshToken entity = repository.findByJtiAndRevocadoFalse(jti)
                .orElseThrow(() -> new RuntimeException("TOKEN NO EXISTE"));

        if (entity.getExpiracion().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("TOKEN EXPIRADO");
        }

        if (!encoder.matches(rawToken, entity.getTokenHash())) {
            throw new RuntimeException("TOKEN INVALIDO");
        }

        return entity;
    }

    @Transactional
    public String rotate(RefreshToken token) {

        token.setRevocado(true);
        repository.save(token);

        return create(token.getUsuario());
    }

    @Transactional
    public void revoke(String fullToken) {

        RefreshToken token = validate(fullToken);

        token.setRevocado(true);
        repository.save(token);
    }

    @Transactional
    public void revokeAllByUser(Long userId) {
        repository.revokeAllByUserId(userId);
    }

    @Transactional
    public void cleanExpired() {
        repository.deleteExpiredOrRevoked(OffsetDateTime.now());
    }

    private String generateToken() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}