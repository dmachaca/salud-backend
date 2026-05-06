package com.pe.model.dto.response.cita;

public record CitaOutputDto(
        Long id,
        String tipoAtencion,
        String fecha,
        String hora,
        String centroMedico,
        String direccion,
        String especialidad,
        String medico,
        String paciente,
        String estado
) {}
