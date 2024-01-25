package com.example.api.exception;

import com.example.api.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        final Map<String, String> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ApiError> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.ok(ApiError.builder()
                .message(exception.getMessage())
                .build());
    }
}
