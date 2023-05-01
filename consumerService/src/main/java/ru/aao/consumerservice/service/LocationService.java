package ru.aao.consumerservice.service;

import ru.aao.consumerservice.type.dto.Location;

import java.util.List;

public interface LocationService {
    void saveLocation(Location location);
    List<Location> getAllLocationsByDeviceId(Long deviceId);
}
