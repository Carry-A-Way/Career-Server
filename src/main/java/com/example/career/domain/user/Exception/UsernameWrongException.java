package com.example.career.domain.user.Exception;

public class UsernameWrongException extends RuntimeException{
    public UsernameWrongException(String username) {
        super("Username not found: " + username);
    }
}