package com.example.eventservice.exception;

public class MessageSerializeException extends ApplicationException {
    public MessageSerializeException(Exception e) {
        super(e);
    }
}
