package ru.practicum.ewm_main.event.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.event.dto.location.LocationDto;
import ru.practicum.ewm_main.event.enums.UserStateAction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdatePrivateDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    LocationDto locationDto;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    String title;
}
