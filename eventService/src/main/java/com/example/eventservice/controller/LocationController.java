package com.example.eventservice.controller;

import com.example.eventservice.dto.Location;
import com.example.eventservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping(value = "/setLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setClientLocation(@RequestBody Location location) {
        locationService.sendMessage(location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
