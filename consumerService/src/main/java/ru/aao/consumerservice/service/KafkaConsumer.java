package ru.aao.consumerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.aao.consumerservice.type.dto.Location;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer implements Consumer {

    private static final String topicName = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final LocationService service;

    @Override
    @KafkaListener(topics = topicName)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consume {}", message);
        Location location = objectMapper.readValue(message, Location.class);
        service.saveLocation(location);
    }
}
