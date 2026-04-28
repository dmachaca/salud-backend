package com.pe.service;

import com.pe.model.dto.request.auth.LoginInputDto;
import com.pe.model.dto.request.auth.RefreshInputDto;
import com.pe.model.dto.response.auth.AuthOutputDto;

public interface IAuthService {

    AuthOutputDto login(LoginInputDto request);

    AuthOutputDto refresh(RefreshInputDto request);

    void logout(String refreshToken);

    void logoutAll(Long userId); // opcional enterprise
}