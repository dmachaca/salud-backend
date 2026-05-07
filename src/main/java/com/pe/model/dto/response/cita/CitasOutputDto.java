package com.pe.model.dto.response.cita;

import java.time.OffsetDateTime;

public record CitasOutputDto(
        Long id,
        String paciente,
        String personal,
        String establecimiento,
        String estado,
        String tipo,
        OffsetDateTime fechaHora
) {
}