package com.pe.exception.global;

import com.pe.exception.dto.ErrorResponse;
import com.pe.exception.dto.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

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

        ex.printStackTrace(); // log temporal

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                null
        );
    }
}