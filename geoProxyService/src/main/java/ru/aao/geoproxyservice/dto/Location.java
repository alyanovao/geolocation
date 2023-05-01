package ru.aao.geoproxyservice.dto;

import lombok.Builder;

/**
 * @param latitude  широта
 * @param longitude долгота
 * @param bearing   азимут
 * @param altitude  высота
 * @param accuracy  точность
 */
@Builder
public record Location(
        Long clientId,
        Long timestamp,
        Double latitude,
        Double longitude,
        Double speed,
        Double bearing,
        Double altitude,
        Double accuracy,
        Double batteryLevel,
        String userAgent
) {}