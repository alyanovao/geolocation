package ru.aao.consumerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    void consumeMessage(String message) throws JsonProcessingException;
}
