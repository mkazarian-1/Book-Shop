package org.example.bookshop.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.example.bookshop.exception.DuplicateException;
import org.example.bookshop.exception.OrderProcessingException;
import org.example.bookshop.exception.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError
                    ? ((FieldError) error).getField() : null;
            String errorMessage = error.getDefaultMessage();

            errors.put(Objects.requireNonNullElse(fieldName, "globalError"), errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Map<String, String>> handleRegistrationException(
            RegistrationException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateException(
            DuplicateException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(
            EntityNotFoundException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<Map<String, String>> handleOrderProcessingException(
            OrderProcessingException e) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
