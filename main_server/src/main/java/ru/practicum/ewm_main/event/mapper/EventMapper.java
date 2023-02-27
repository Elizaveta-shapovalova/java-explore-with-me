package ru.practicum.ewm_main.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.practicum.ewm_main.category.mapper.CategoryMapper;
import ru.practicum.ewm_main.event.dto.event.*;
import ru.practicum.ewm_main.event.enums.State;
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
                .eventDate(event.getEventDate().format(DATE_TIME_FORMATTER))
                .id(event.getId())
                .initiator(UserMapper.toUserShotDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().format(DATE_TIME_FORMATTER) : null)
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
                .eventDate(eventUpdateAdminDto.getEventDate() != null && !eventUpdateAdminDto.getEventDate().isBlank() ?
                        TimeMapper.getDateTime(eventUpdateAdminDto.getEventDate()) : null)
                .location(eventUpdateAdminDto.getLocationDto() != null ? LocationMapper.toLocation(eventUpdateAdminDto.getLocationDto()) : null)
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
                .eventDate(eventUpdatePrivateDto.getEventDate() != null && !eventUpdatePrivateDto.getEventDate().isBlank() ?
                        TimeMapper.getDateTime(eventUpdatePrivateDto.getEventDate()) : null)
                .location(eventUpdatePrivateDto.getLocationDto() != null ? LocationMapper.toLocation(eventUpdatePrivateDto.getLocationDto()) : null)
                .paid(eventUpdatePrivateDto.getPaid())
                .participantLimit(eventUpdatePrivateDto.getParticipantLimit())
                .requestModeration(eventUpdatePrivateDto.getRequestModeration())
                .title(eventUpdatePrivateDto.getTitle())
                .build();
    }

    public static Event toEventFromNew(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(TimeMapper.getDateTime(newEventDto.getEventDate()))
                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .state(State.PENDING)
                .build();
    }
}
