package com.pe.model.dto.request.usuario;

import jakarta.validation.constraints.*;

public record UsuarioInputDto(

        Long id,

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 50)
        String nombreUsuario,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String correo,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
        String clave,

        Boolean activo

) {}