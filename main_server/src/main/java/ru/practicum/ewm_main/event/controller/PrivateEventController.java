package ru.practicum.ewm_main.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.dto.EventDto;
import ru.practicum.ewm_main.event.dto.EventFullDto;
import ru.practicum.ewm_main.event.dto.EventShortDto;
import ru.practicum.ewm_main.event.dto.EventUpdatePrivateDto;
import ru.practicum.ewm_main.event.mapper.EventMapper;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.service.EventService;
import ru.practicum.ewm_main.request.dto.RequestConfirmedDto;
import ru.practicum.ewm_main.request.dto.RequestDto;
import ru.practicum.ewm_main.request.dto.RequestShortDto;
import ru.practicum.ewm_main.request.mapper.RequestMapper;
import ru.practicum.ewm_main.request.service.RequestService;
import ru.practicum.ewm_main.validation.Create;
import ru.practicum.ewm_main.validation.Update;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrivateEventController {
    EventService eventService;
    RequestService requestService;

    @GetMapping
    public List<EventDto> getAllPrivate(@PathVariable Long userId,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                        @Positive @RequestParam(defaultValue = "10") Integer size) {
        List<Event> events = eventService.getAllPrivate(userId, from, size);
        if (!events.isEmpty()) {
            eventService.addView(events);
        }
        return EventMapper.toListEventDto(events);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @Validated({Create.class}) @RequestBody EventShortDto eventShortDto) {
        return EventMapper.toEventFullDto(eventService.create(userId, eventShortDto.getCategory(),
                EventMapper.toEventFromShort(eventShortDto)));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByIdPrivate(@PathVariable Long userId, @PathVariable Long eventId) {
        Event event = eventService.getByIdPrivate(userId, eventId);
        eventService.addView(List.of(event));
        return EventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updatePrivate(@PathVariable Long userId, @PathVariable Long eventId,
                                      @Validated({Update.class}) @RequestBody EventUpdatePrivateDto eventUpdatePrivateDto) {
        System.out.println("PrivateEventController.updatePrivate");
        return EventMapper.toEventFullDto(eventService.updatePrivate(userId, eventId, eventUpdatePrivateDto.getCategory(),
                eventUpdatePrivateDto.getStateAction(), EventMapper.toEventFromUpdatePrivate(eventUpdatePrivateDto)));
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getAllPrivate(@PathVariable Long userId, @PathVariable Long eventId) {
        return RequestMapper.toListRequestDto(requestService.getAllPrivate(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public RequestConfirmedDto updateStatusPrivate(@PathVariable Long userId, @PathVariable Long eventId,
                                                   @Validated({Update.class}) @RequestBody RequestShortDto requestShortDto) {
        return RequestMapper.toRequestConfirmedDto(requestService.updateStatusPrivate(userId, eventId,
                requestShortDto.getRequestIds(), requestShortDto.getStatus()));
    }
}
