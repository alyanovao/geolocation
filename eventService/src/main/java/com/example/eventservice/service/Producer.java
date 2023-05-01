package com.example.eventservice.service;

import com.example.eventservice.dto.Location;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface Producer {
    String sendMessage(Location location) throws JsonProcessingException;
}
