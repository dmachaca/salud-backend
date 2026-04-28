package com.pe.service.impl;

import com.pe.model.dto.request.auth.LoginInputDto;
import com.pe.model.dto.request.auth.RefreshInputDto;
import com.pe.model.dto.response.auth.AuthOutputDto;
import com.pe.model.entity.RefreshToken;
import com.pe.security.jwt.JwtService;
import com.pe.security.model.UserPrincipal;
import com.pe.security.service.RefreshTokenService;
import com.pe.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshService;

    @Override
    @Transactional
    public AuthOutputDto login(LoginInputDto request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var principal = (UserPrincipal) authentication.getPrincipal();
        var usuario = principal.getUsuario();

        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = refreshService.create(usuario);

        return new AuthOutputDto(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthOutputDto refresh(RefreshInputDto request) {

        RefreshToken tokenEntity = refreshService.validate(request.refreshToken());

        String newAccess = jwtService.generateAccessToken(tokenEntity.getUsuario());

        String newRefresh = refreshService.rotate(tokenEntity);

        return new AuthOutputDto(newAccess, newRefresh);
    }

    // =========================
    // 🚪 LOGOUT (un dispositivo)
    // =========================
    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshService.revoke(refreshToken);
    }

    // =========================
    // 🚪 LOGOUT GLOBAL
    // =========================
    @Override
    @Transactional
    public void logoutAll(Long userId) {
        refreshService.revokeAllByUser(userId);
    }
}