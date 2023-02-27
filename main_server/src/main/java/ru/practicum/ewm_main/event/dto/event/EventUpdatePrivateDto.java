package ru.practicum.ewm_main.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.event.dto.location.LocationDto;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.validation.Update;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdatePrivateDto {
    @Size(min = 20, max = 2000, groups = {Update.class})
    String annotation;
    Long category;
    @Size(min = 20, max = 7000, groups = {Update.class})
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    LocationDto locationDto;
    Boolean paid;
    @PositiveOrZero(groups = {Update.class})
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    @Size(min = 3, max = 120, groups = {Update.class})
    String title;
}
