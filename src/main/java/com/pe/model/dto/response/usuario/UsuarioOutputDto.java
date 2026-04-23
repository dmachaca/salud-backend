package com.pe.model.dto.response.usuario;

public record UsuarioOutputDto(

        Long id,
        String nombreUsuario,
        String correo,
        Boolean activo,

        // datos de persona embebidos (muy útil)
        Long personaId,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        String dni

) {}