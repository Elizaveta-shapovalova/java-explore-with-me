package ru.practicum.ewm_main.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.category.dto.CategoryDto;
import ru.practicum.ewm_main.user.dto.UserShotDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String eventDate;
    Long id;
    UserShotDto initiator;
    Boolean paid;
    String title;
    Long views;
}
