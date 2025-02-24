package com.example.iManager.exceptions;

public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message, Exception e) {
        super(message);
    }
}
