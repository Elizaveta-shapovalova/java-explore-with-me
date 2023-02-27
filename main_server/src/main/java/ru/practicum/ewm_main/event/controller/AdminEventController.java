package ru.practicum.ewm_main.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.dto.event.EventFullDto;
import ru.practicum.ewm_main.event.dto.event.EventUpdateAdminDto;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.event.mapper.EventMapper;
import ru.practicum.ewm_main.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/events")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminEventController {
    EventService eventService;

    @GetMapping
    public List<EventFullDto> getAllAdmin(@RequestParam(required = false) List<Long> users,
                                          @RequestParam(required = false) List<String> states,
                                          @RequestParam(required = false) List<Long> categories,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                          @Positive @RequestParam(defaultValue = "10") Integer size) {
        return EventMapper.toListEventFullDtoFromPage(eventService.getAllAdmin(users, State.fromList(states), categories, rangeStart,
                rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateAdmin(@PathVariable Long eventId, @RequestBody EventUpdateAdminDto eventUpdateAdminDto) {
        return EventMapper.toEventFullDto(eventService.updateAdmin(eventId, eventUpdateAdminDto.getCategory(),
                eventUpdateAdminDto.getStateAction(), EventMapper.toEventFromUpdateAdmin(eventUpdateAdminDto)));
    }
}
