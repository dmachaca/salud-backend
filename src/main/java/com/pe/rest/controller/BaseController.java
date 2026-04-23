package com.pe.rest.controller;

import com.pe.exception.*;
import com.pe.model.dto.response.DownloadFileOutputDto;
import com.pe.model.dto.response.GenericResponse;
import com.pe.utils.ErrorMensajes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Slf4j
public class BaseController {

    public ResponseEntity<GenericResponse> handleRequest(Callable<GenericResponse> action) {
        try {
            GenericResponse result = action.call();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException | BadRequestException | AccesoNoPermitidoException | UsuarioException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            GenericResponse errorResponse = buildErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    public ResponseEntity<Object> handleRequest(Supplier<byte[]> action, String filename, MediaType mediaType) {
        try {
            byte[] result = action.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .header("X-Content-Type-Options", "nosniff")
                    .header("Content-Security-Policy", "default-src 'none'; frame-ancestors 'none'; base-uri 'none';")
                    .contentType(mediaType)
                    .body(result);
        } catch(NotFoundException | InternalServerErrorException e){
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            GenericResponse errorResponse = buildErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    public ResponseEntity<Object> handleRequest(Supplier<DownloadFileOutputDto> action) {
        try {
            DownloadFileOutputDto result = action.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + result.getFilename())
                    .contentType(MediaType.parseMediaType(result.getFormato()))
                    .body(result.getFile());
        } catch(NotFoundException |  DownloadFileException | InternalServerErrorException e){
            log.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            GenericResponse errorResponse = buildErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    private GenericResponse buildErrorResponse(Exception e) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ErrorMensajes.ERROR);
        log.error("Error en la operación:", e);
        return response;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<GenericResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GenericResponse> handleNotFoundException(NotFoundException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GenericResponse> handleBadRequestException(BadRequestException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccesoNoPermitidoException.class)
    public ResponseEntity<GenericResponse> handleAccesoNoPermitidoException(AccesoNoPermitidoException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<GenericResponse> handleUsuarioException(UsuarioException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<GenericResponse> handleFileUploadException(FileUploadException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(DownloadFileException.class)
    public ResponseEntity<GenericResponse> handleDownloadFileException(DownloadFileException ex) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
