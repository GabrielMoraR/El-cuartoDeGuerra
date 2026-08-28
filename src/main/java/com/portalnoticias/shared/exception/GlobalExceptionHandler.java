package com.portalnoticias.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portalnoticias.shared.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex){

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){

        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message("Ha ocurrido un error interno.")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
    @ExceptionHandler(ConflictException.class)
public ResponseEntity<ApiResponse<Object>> handleConflict(ConflictException ex) {

    ApiResponse<Object> response = ApiResponse.builder()
            .success(false)
            .message(ex.getMessage())
            .data(null)
            .build();

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

}

}