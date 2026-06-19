package com.rajat.expense_tracker.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email already exist "+email);
    }
}
