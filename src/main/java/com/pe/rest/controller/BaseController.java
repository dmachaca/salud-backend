package com.pe.rest.controller;

import com.pe.exception.*;
import com.pe.model.dto.response.DownloadFileOutputDto;
import com.pe.model.dto.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseController {

    public ResponseEntity<GenericResponse> handleRequest(Callable<GenericResponse> action) {
        try {
            return ResponseEntity.ok(action.call());
        } catch (RuntimeException e) {
            log.error("Error en request", e);
            throw e; // solo runtime
        } catch (Exception e) {
            log.error("Error en request", e);

            throw new InternalServerErrorException("Error interno del servidor");
        }
    }

    public ResponseEntity<Object> handleRequest(
            Supplier<byte[]> action,
            String filename,
            MediaType mediaType
    ) {
        try {
            byte[] result = action.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(mediaType)
                    .body(result);
        } catch (Exception e) {
            log.error("Error en descarga", e);
            throw e;
        }
    }

    public ResponseEntity<Object> handleRequest(
            Supplier<DownloadFileOutputDto> action
    ) {
        try {
            DownloadFileOutputDto result = action.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFilename())
                    .contentType(MediaType.parseMediaType(result.getFormato()))
                    .body(result.getFile());
        } catch (Exception e) {
            log.error("Error en descarga", e);
            throw e;
        }
    }
}
