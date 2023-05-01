package ru.aao.consumerservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConsumerConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
