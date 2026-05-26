package com.aquienllamo.aquienllamo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundEx.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundEx(UserNotFoundEx ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO.builder()
                .status(404)
                .mensaje(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }
}
