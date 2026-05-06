package com.pe.model.dto.response.cita;

public record DisponibilidadOutputDto(
        Long id,
        String fecha,
        String hora,
        String label
) {}
