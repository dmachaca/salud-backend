package com.pe.model.dto.request.persona;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PersonaInputDto(

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 100, message = "Los nombres no deben exceder 100 caracteres")
        String nombres,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 100)
        String apellidoPaterno,

        @Size(max = 100)
        String apellidoMaterno,

        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
        String dni,

        LocalDate fechaNacimiento,

        @Size(max = 1)
        String genero,

        @Size(max = 20)
        String telefono,

        String direccion

) {}