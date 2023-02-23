package ru.practicum.ewm_main.event.service;

import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.event.enums.AdminStateAction;
import ru.practicum.ewm_main.event.enums.SortType;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<Event> getAllPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from, Integer size);


    Event getByIdPublic(Long eventId);

    Page<Event> getAllAdmin(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                            LocalDateTime rangeEnd, Integer from, Integer size);

    Event updateAdmin(Long eventId, Long category, AdminStateAction stateAction, Event event);

    List<Event> getAllPrivate(Long userId, Integer from, Integer size);

    Event create(Long userId, Long category, Event event);

    Event getByIdPrivate(Long userId, Long eventId);

    Event updatePrivate(Long userId, Long eventId, Long category, UserStateAction stateAction, Event event);

    void addView(List<Event> events);
}
