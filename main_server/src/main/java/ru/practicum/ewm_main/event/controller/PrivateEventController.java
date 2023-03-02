package ru.practicum.ewm_main.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.dto.event.EventDto;
import ru.practicum.ewm_main.event.dto.event.EventFullDto;
import ru.practicum.ewm_main.event.dto.event.EventUpdatePrivateDto;
import ru.practicum.ewm_main.event.dto.event.NewEventDto;
import ru.practicum.ewm_main.event.mapper.EventMapper;
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
        return EventMapper.toListEventDto(eventService.getAllPrivate(userId, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @Validated({Create.class}) @RequestBody NewEventDto newEventDto) {
        return EventMapper.toEventFullDto(eventService.create(userId, newEventDto.getCategory(),
                EventMapper.toEventFromNew(newEventDto)));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByIdPrivate(@PathVariable Long userId, @PathVariable Long eventId) {
        return EventMapper.toEventFullDto(eventService.getByIdPrivate(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updatePrivate(@PathVariable Long userId, @PathVariable Long eventId,
                                      @Validated({Update.class}) @RequestBody EventUpdatePrivateDto eventUpdatePrivateDto) {
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
