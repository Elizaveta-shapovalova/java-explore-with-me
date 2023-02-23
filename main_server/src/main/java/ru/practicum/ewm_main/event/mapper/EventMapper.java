package ru.practicum.ewm_main.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.category.mapper.CategoryMapper;
import ru.practicum.ewm_main.event.dto.*;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.mapper.TimeMapper;
import ru.practicum.ewm_main.user.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm_main.constant.Constant.DATE_TIME_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static EventDto toEventDto(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DATE_TIME_FORMATTER))
                .id(event.getId())
                .initiator(UserMapper.toUserShotDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static List<EventDto> toListEventDto(List<Event> events) {
        return events.stream().map(EventMapper::toEventDto).collect(Collectors.toList());
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(DATE_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toUserShotDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(DATE_TIME_FORMATTER))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static List<EventFullDto> toListEventFullDtoFromPage(Page<Event> events) {
        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public static Event toEventFromUpdateAdmin(EventUpdateAdminDto eventUpdateAdminDto) {
        return Event.builder()
                .annotation(eventUpdateAdminDto.getAnnotation())
                .description(eventUpdateAdminDto.getDescription())
                .location(LocationMapper.toLocation(eventUpdateAdminDto.getLocation()))
                .paid(eventUpdateAdminDto.getPaid())
                .participantLimit(eventUpdateAdminDto.getParticipantLimit())
                .requestModeration(eventUpdateAdminDto.getRequestModeration())
                .title(eventUpdateAdminDto.getTitle())
                .build();
    }

    public static Event toEventFromUpdatePrivate(EventUpdatePrivateDto eventUpdatePrivateDto) {
        return Event.builder()
                .annotation(eventUpdatePrivateDto.getAnnotation())
                .description(eventUpdatePrivateDto.getDescription())
                .location(LocationMapper.toLocation(eventUpdatePrivateDto.getLocation()))
                .paid(eventUpdatePrivateDto.getPaid())
                .participantLimit(eventUpdatePrivateDto.getParticipantLimit())
                .requestModeration(eventUpdatePrivateDto.getRequestModeration())
                .title(eventUpdatePrivateDto.getTitle())
                .build();
    }

    public static Event toEventFromShort(EventShortDto eventShortDto) {
        return Event.builder()
                .annotation(eventShortDto.getAnnotation())
                .description(eventShortDto.getDescription())
                .eventDate(TimeMapper.getDateTime(eventShortDto.getEventDate()))
                .location(LocationMapper.toLocation(eventShortDto.getLocationDto()))
                .paid(eventShortDto.getPaid())
                .participantLimit(eventShortDto.getParticipantLimit())
                .requestModeration(eventShortDto.getRequestModeration())
                .title(eventShortDto.getTitle())
                .build();
    }
}