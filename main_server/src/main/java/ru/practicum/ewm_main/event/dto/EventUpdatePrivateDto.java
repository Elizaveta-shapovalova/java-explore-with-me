package ru.practicum.ewm_main.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.annotation.StartTimeValid;
import ru.practicum.ewm_main.event.enums.UserStateAction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@StartTimeValid
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdatePrivateDto {
    String annotation;
    Long category;
    String description;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    String title;
}
