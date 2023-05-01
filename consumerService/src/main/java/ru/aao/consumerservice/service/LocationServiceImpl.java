package ru.aao.consumerservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.aao.consumerservice.repository.EventRepository;
import ru.aao.consumerservice.type.dao.Event;
import ru.aao.consumerservice.type.dto.Location;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final EventRepository repository;

    @Override
    public void saveLocation(Location location) {
        val event = Event.toDao(location);
        repository.save(event);
        log.info(location.toString());
    }

    @Override
    public List<Location> getAllLocationsByDeviceId(Long deviceId) {
        return repository.findByDeviceId(deviceId).stream().map(event -> Location.toDto(event)).toList();
    }
}
