package com.pe.security.service;

import com.pe.model.entity.Usuario;
import com.pe.model.entity.RefreshToken;
import com.pe.repository.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder encoder;

    public String create(Usuario usuario) {

        String raw = generateToken();
        String hash = encoder.encode(raw);

        RefreshToken entity = RefreshToken.builder()
                .usuario(usuario)
                .tokenHash(hash)
                .jti(generateToken())
                .expiracion(OffsetDateTime.now().plusDays(7))
                .revocado(false)
                .build();

        refreshTokenRepository.save(entity);

        return raw;
    }

    public RefreshToken validate(String token, Long userId) {

        return refreshTokenRepository.findByUsuarioIdAndRevocadoFalse(userId)
                .stream()
                .filter(t -> encoder.matches(token, t.getTokenHash()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("REFRESH INVALIDO"));
    }

    public void revoke(RefreshToken token) {
        token.setRevocado(true);
        refreshTokenRepository.save(token);
    }

    private String generateToken() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}