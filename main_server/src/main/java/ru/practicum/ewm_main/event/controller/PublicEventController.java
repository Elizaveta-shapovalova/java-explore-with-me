package ru.practicum.ewm_main.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.client.EventClient;
import ru.practicum.ewm_main.event.dto.event.EventDto;
import ru.practicum.ewm_main.event.dto.event.EventFullDto;
import ru.practicum.ewm_main.event.enums.SortType;
import ru.practicum.ewm_main.event.mapper.EventMapper;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PublicEventController {
    EventService eventService;
    EventClient eventClient;

    @GetMapping
    public List<EventDto> getAllPublic(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Long> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(required = false) String stringSort,
                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                       @Positive @RequestParam(defaultValue = "10") Integer size,
                                       HttpServletRequest httpServletRequest) {
        SortType sort = SortType.from(stringSort);
        List<Event> events = eventService.getAllPublic(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        eventClient.createHit(httpServletRequest);
        return EventMapper.toListEventDto(events);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByIdPublic(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        Event event = eventService.getByIdPublic(eventId);
        eventClient.createHit(httpServletRequest);
        return EventMapper.toEventFullDto(event);
    }
}
