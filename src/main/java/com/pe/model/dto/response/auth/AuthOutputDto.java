package com.pe.model.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthOutputDto {
    private String accessToken;
    private String refreshToken;
}