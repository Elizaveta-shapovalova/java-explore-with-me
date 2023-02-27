package ru.practicum.ewm_main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm_main.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long userId);

    Request findByEventIdAndRequesterId(Long eventId, Long userId);

    List<Request> findAllByEventId(Long eventId);
}
