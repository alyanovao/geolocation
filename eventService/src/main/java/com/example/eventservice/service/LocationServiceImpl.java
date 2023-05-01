package com.example.eventservice.service;

import com.example.eventservice.dto.Location;
import com.example.eventservice.exception.MessageSerializeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final Producer producer;
    @Override
    public String sendMessage(Location location) {
        log.info("sendMessage :: location: " + location);
        try {
            return producer.sendMessage(location);
        } catch (JsonProcessingException e) {
            log.error("sendMessage :: " + e);
            throw new MessageSerializeException(e);
        }
    }
}
