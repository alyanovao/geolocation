package com.example.eventservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.springframework.kafka.support.serializer.JsonDeserializer.VALUE_DEFAULT_TYPE;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${topic.name}")
    private String topicName;

    //@Value("${kafka.producer.name}")
    //private String kafkaProducerName;

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        //properties.put(CLIENT_ID_CONFIG, kafkaProducerName);
        //properties.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //properties.put(ACKS_CONFIG, "1");
        //properties.put(RETRIES_CONFIG, 1);
        //properties.put(BATCH_SIZE_CONFIG, 16384);
        properties.put(LINGER_MS_CONFIG, 10);
        //properties.put(BUFFER_MEMORY_CONFIG, 33_554_432);
        properties.put(MAX_BLOCK_MS_CONFIG, 1_0000);
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(VALUE_DEFAULT_TYPE, JsonSerializer.class);
        properties.put("OBJECT_MAPPER", ObjectMapper.class);

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
                .name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
