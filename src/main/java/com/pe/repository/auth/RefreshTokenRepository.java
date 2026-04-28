package com.pe.repository.auth;

import com.pe.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    List<RefreshToken> findByUsuarioIdAndRevocadoFalse(Long usuarioId);
}