package com.pe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class DownloadFileException extends RuntimeException {
    public DownloadFileException(String message) {
        super(message);
    }

}
