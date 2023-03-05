package ru.practicum.ewm_main.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.event.dto.location.LocationDto;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotBlank(groups = {Create.class})
    @Size(min = 20, max = 2000, groups = {Create.class})
    String annotation;
    @NotNull(groups = {Create.class})
    Long category;
    @NotBlank(groups = {Create.class})
    @Size(min = 20, max = 7000, groups = {Create.class})
    String description;
    @NotNull(groups = {Create.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull(groups = {Create.class})
    LocationDto location;
    boolean paid = false;
    @PositiveOrZero(groups = {Create.class})
    long participantLimit = 0L;
    boolean requestModeration = true;
    @NotBlank(groups = {Create.class})
    @Size(min = 3, max = 120, groups = {Create.class})
    String title;
    UserStateAction stateAction;
    @PositiveOrZero(groups = {Create.class})
    double price = 0;
}
