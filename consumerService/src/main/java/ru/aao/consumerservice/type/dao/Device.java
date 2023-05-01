package ru.aao.consumerservice.type.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@RequiredArgsConstructor
@Table(name = "device")
public class Device {

    @Id
    private final Long id;
    private final String name;
}
