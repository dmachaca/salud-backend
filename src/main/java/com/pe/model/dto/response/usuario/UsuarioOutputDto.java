package com.pe.model.dto.response.usuario;

public record UsuarioOutputDto(
            Long id,
         String nombreUsuario,
         String correoElectronico,
         Boolean activo,
         Long personaId) {}
