package com.example.iManager.exceptions;

public class EmailSendingFailedException extends RuntimeException {
    public EmailSendingFailedException(String message, Exception e) {
        super(message);
    }
}
