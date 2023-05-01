package ru.aao.consumerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.aao.consumerservice.service.LocationService;
import ru.aao.consumerservice.type.dto.Location;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final LocationService locationService;

    @GetMapping(value = "/getLocations/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Location>> getLocationById(@PathVariable("deviceId") Long deviceId) {
        return new ResponseEntity<List<Location>>(locationService.getAllLocationsByDeviceId(deviceId), HttpStatus.OK);
    }
}
