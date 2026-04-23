package com.pe.model.dto.request.usuario;

public record UsuarioInputDto( Long id, // null = create, not null = update
         Long personaId,
         String nombreUsuario,
         String correo,
         String clave,//solo para create o cambio password
         Boolean activo) {}
