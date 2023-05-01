package com.example.eventservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {

    private Long clientId;

    private Long timestamp;
    //широта
    private Double latitude;
    //долгота
    private Double longitude;

    private Double speed;
    //азимут
    private Double bearing;
    //высота
    private Double altitude;
    //точность
    private Double accuracy;

    private Double batteryLevel;

    private String userAgent;
}
