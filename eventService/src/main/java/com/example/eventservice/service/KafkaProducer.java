package com.example.eventservice.service;

import com.example.eventservice.dto.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer implements Producer {

    @Value("${topic.name}")
    private String topicName;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public String sendMessage(Location location) throws JsonProcessingException {
        String locationMessage = objectMapper.writeValueAsString(location);
        kafkaTemplate.send(topicName, locationMessage);
        log.info("send {}", locationMessage);
        return "message send";
    }
}
