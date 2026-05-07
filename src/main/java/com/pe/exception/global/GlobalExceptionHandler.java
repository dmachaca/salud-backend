package com.pe.exception.global;

import com.pe.exception.AccesoNoPermitidoException;
import com.pe.exception.UsuarioException;
import com.pe.exception.dto.ErrorResponse;
import com.pe.exception.dto.ValidationError;
import com.pe.security.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            List<ValidationError> details
    ) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .timestamp(OffsetDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .details(details)
                        .build()
        );
    }

    // =========================
    // VALIDATION
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ValidationError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(messageSource.getMessage(error, Locale.getDefault()))
                        .build())
                .toList();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                errors
        );
    }

    // =========================
    // MALFORMED JSON
    // =========================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson() {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON request",
                null
        );
    }

    // =========================
    // METHOD NOT ALLOWED
    // =========================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed() {
        return buildErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "HTTP method not allowed",
                null
        );
    }

    // =========================
    // ACCESS DENIED
    // =========================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied() {
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                "Access denied",
                null
        );
    }

    // =========================
    // GENERAL ERROR
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {

        log.error("Error inesperado", ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                null
        );
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtException() {

        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Token inválido o expirado",
                null
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Usuario o contraseña incorrectos",
                null
        );
    }

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioException(UsuarioException ex) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(AccesoNoPermitidoException.class)
    public ResponseEntity<ErrorResponse> handleAccesoNoPermitido(
            AccesoNoPermitidoException ex
    ) {

        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                null
        );
    }

}