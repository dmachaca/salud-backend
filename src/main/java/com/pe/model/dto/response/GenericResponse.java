package com.pe.model.dto.response;

import lombok.Data;

@Data
public class GenericResponse {
    private Boolean success;
    private String message;
    private String messageInterno;
    private String titulo;
    private String tipoIcono;
    private Object data;
}
