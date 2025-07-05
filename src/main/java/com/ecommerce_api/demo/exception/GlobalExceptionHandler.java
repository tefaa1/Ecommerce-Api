package com.ecommerce_api.demo.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath().toString();
            errors.put(path.substring(path.lastIndexOf('.') + 1), cv.getMessage());
        });
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "You are not authorized to perform this action", null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Database error: maybe duplicate value or foreign key violation", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        if (ex instanceof IllegalArgumentException ||
                ex instanceof MethodArgumentTypeMismatchException ||
                ex instanceof MissingServletRequestParameterException ||
                ex instanceof HttpMessageNotReadableException ||
                ex instanceof TypeMismatchException ||
                ex instanceof HttpRequestMethodNotSupportedException) {

            return buildResponse(HttpStatus.BAD_REQUEST, "Invalid request: " + ex.getMessage(), null);
        }

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", null);
    }


    private ResponseEntity<?> buildResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());
        body.put("data", data);
        return new ResponseEntity<>(body, status);
    }
}
