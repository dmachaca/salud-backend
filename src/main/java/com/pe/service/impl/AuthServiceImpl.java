package com.pe.service.impl;

import com.pe.model.dto.request.auth.LoginInputDto;
import com.pe.model.dto.request.auth.RefreshInputDto;
import com.pe.model.dto.response.auth.AuthOutputDto;
import com.pe.model.entity.Usuario;
import com.pe.repository.UsuarioRepository;
import com.pe.security.jwt.JwtService;
import com.pe.security.model.UserPrincipal;
import com.pe.security.service.RefreshTokenService;
import com.pe.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        String access = jwtService.generateAccessToken(usuario);
        String refresh = refreshService.create(usuario);

        return new AuthOutputDto(access, refresh);
    }

    @Override
    public AuthOutputDto refresh(RefreshInputDto request) {
        return null;
    }
}