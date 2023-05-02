package ru.aao.consumerservice.type.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.aao.consumerservice.service.Util;
import ru.aao.consumerservice.type.dto.Location;

import java.sql.Timestamp;

@Getter
@Builder
@RequiredArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    private final Long id;

    private final Long deviceId;

    @NonNull
    private final Timestamp timestamp;

    //широта
    @NonNull
    private final Double latitude;

    //долгота
    @NonNull
    private final Double longitude;

    @NonNull
    private final Double speed;

    //азимут
    @NonNull
    private final Double bearing;

    //высота
    @NonNull
    private final Double altitude;

    //точность
    @NonNull
    private final Double accuracy;

    @NonNull
    @MappedCollection(idColumn = "id")
    private DeviceState deviceState;

    public static Event toDao(Location location) {
        return Event.builder()
                .deviceId(location.getClientId())
                .timestamp(Util.toTimestamp(location.getTimestamp()))
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .speed(location.getSpeed())
                .bearing(location.getBearing())
                .altitude(location.getAltitude())
                .accuracy(location.getAccuracy())
                .deviceState(DeviceState.builder()
                        .deviceId(location.getClientId())
                        .timestamp(Util.toTimestamp(location.getTimestamp()))
                        .batteryLevel(location.getBatteryLevel())
                        .userAgent(location.getUserAgent())
                        .build())
                .build();
    }
}
