package com.pe.model.dto.response.cita;

public record TipoAtencionOutputDto(
        Long id,
        String codigo,
        String nombre,
        String descripcion
) {}
