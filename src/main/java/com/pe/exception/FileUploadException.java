package com.pe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
