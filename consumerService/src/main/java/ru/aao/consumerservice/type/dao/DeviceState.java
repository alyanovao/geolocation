package ru.aao.consumerservice.type.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Getter
@Builder
@RequiredArgsConstructor
@Table(name = "devicestate")
public class DeviceState {
    @Id
    @Column(value = "id")
    private final Long eventId;

    @Column(value = "device_id")
    private final Long deviceId;

    @Column(value = "timestamp")
    private final Timestamp timestamp;

    @Column(value = "useragent")
    private final String userAgent;

    @Column(value = "batterylevel")
    private final Double batteryLevel;

}