package com.example.career.domain.user.Exception;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException() {
        super("Incorrect password");
    }
}