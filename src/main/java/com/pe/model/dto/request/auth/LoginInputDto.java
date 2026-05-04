package com.pe.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginInputDto(
        @NotBlank(message = "El username de usuario es obligatorio")
        String username,
        @NotBlank(message = "El password de usuario es obligatorio")
        String password,

        @NotBlank(message = "El recaptcha es obligatorio")
        String recaptcha
) {}
