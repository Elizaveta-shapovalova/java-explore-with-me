package ru.practicum.ewm_main.request.service;

import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.Status;

import java.util.List;

public interface RequestService {
    List<Request> getAll(Long userId);

    Request create(Long userId, Long eventId);

    Request cancel(Long userId, Long requestId);

    List<Request> getAllPrivate(Long userId, Long eventId);

    List<Request> updateStatusPrivate(Long userId, Long eventId, List<Long> requestIds, Status status);
}
