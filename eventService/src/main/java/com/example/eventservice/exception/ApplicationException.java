package com.example.eventservice.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(Exception e) {
        super(e);
    }
}
