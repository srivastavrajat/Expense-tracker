package com.rajat.expense_tracker.exception;

import com.rajat.expense_tracker.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error =
                new ErrorResponse(LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e->
                        errors.put(
                                e.getField(),
                                e.getDefaultMessage())
                );
        return ResponseEntity
                .badRequest()
                .body(errors.toString());
    }
    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpenseNotFoundException(ExpenseNotFoundException e){
        ErrorResponse error=new ErrorResponse(LocalDateTime.now(),e.getMessage(),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}


