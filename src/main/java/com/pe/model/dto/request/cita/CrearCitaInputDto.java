package com.pe.model.dto.request.cita;

import jakarta.validation.constraints.NotNull;

public record CrearCitaInputDto(
        @NotNull Long tipoAtencionId,
        @NotNull Long pacienteId,
        @NotNull Long especialidadId,
        @NotNull Long centroMedicoId,
        @NotNull Long medicoId,
        @NotNull Long disponibilidadId
) {}
