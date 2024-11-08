package com.example.tours.exception;

public class InvalidTourException extends RuntimeException {
    public InvalidTourException(String message) {
        super(message);
    }
}
