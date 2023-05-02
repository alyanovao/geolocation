package ru.aao.geoproxyservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ApplicationException extends RuntimeException {
    public ApplicationException(JsonProcessingException e) {
        super(e);
    }
    public ApplicationException(Exception e) {
        super(e);
    }
}
