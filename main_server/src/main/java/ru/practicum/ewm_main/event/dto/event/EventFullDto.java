package ru.practicum.ewm_main.event.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.event.dto.location.LocationDto;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.user.dto.UserShotDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    Long id;
    UserShotDto initiator;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    String publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}
