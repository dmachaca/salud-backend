package com.pe.model.dto.response.persona;

import java.time.LocalDate;

public record PersonaOutputDto(
        Long id,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        String dni,
        LocalDate fechaNacimiento,
        String genero,
        String telefono,
        String direccion
) {}