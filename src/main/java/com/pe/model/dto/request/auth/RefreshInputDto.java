package com.pe.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshInputDto(
        @NotBlank String refreshToken,
        @NotNull Long userId
) {}