package com.pe.model.dto.response.cita;

public record MedicoOutputDto(
        Long id,
        String nombreCompleto,
        String especialidad,
        String proximaCita
) {}
