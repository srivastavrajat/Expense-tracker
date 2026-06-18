package com.rajat.expense_tracker.exception;

import com.rajat.expense_tracker.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
    ErrorResponse error =
            new ErrorResponse(LocalDateTime.now(),
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value()
            );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}
}
