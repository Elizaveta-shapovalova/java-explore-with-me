package ru.practicum.ewm_main.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.event.enums.AdminStateAction;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateAdminDto {
    String annotation;
    Long category;
    String description;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    String title;
}
