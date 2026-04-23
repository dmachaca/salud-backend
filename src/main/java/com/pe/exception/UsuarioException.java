package com.pe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class UsuarioException extends RuntimeException{
    public UsuarioException(String message) {
        super(message);
    }
}
