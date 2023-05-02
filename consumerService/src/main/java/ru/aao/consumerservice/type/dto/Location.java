package ru.aao.consumerservice.type.dto;

import lombok.*;
import ru.aao.consumerservice.type.dao.Event;

import java.util.Objects;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class Location {

    private final Long clientId;

    private final Long timestamp;
    //широта
    private final Double latitude;
    //долгота
    private final Double longitude;

    private final Double speed;
    //азимут
    private final Double bearing;
    //высота
    private final Double altitude;
    //точность
    private final Double accuracy;

    private final Double batteryLevel;

    private final String userAgent;

    public static Location toDto(Event event) {
        return Location.builder()
                .clientId(event.getDeviceId())
                .timestamp(event.getTimestamp().getTime())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .speed(event.getSpeed())
                .bearing(event.getBearing())
                .altitude(event.getAltitude())
                .accuracy(event.getAccuracy())
                .batteryLevel(Objects.nonNull(event.getDeviceState()) ? event.getDeviceState().getBatteryLevel() : null)
                .userAgent(Objects.nonNull(event.getDeviceState()) ? event.getDeviceState().getUserAgent() : null)
                .build();
    }
}
