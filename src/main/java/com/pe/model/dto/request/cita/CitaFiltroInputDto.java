package com.pe.model.dto.request.cita;

import java.time.LocalDate;

public record CitaFiltroInputDto(

        String paciente,
        Integer estadoCitaId,
        Integer tipoCitaId,
        Long establecimientoId,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
}