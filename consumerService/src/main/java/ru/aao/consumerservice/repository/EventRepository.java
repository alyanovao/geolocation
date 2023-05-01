package ru.aao.consumerservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aao.consumerservice.type.dao.Event;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByDeviceId(Long deviceId);
}
