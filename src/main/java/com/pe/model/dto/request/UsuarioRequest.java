package com.pe.model.dto.request;

import com.pe.model.dto.request.persona.PersonaInputDto;
import com.pe.model.dto.request.usuario.UsuarioInputDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(

        @Valid
        @NotNull(message = "Los datos de la persona son obligatorios")
        PersonaInputDto persona,

        @Valid
        @NotNull(message = "Los datos del usuario son obligatorios")
        UsuarioInputDto usuario

) {}