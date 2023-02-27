package ru.practicum.ewm_main.event.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.event.dto.location.LocationDto;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    String eventDate;
    @NotNull(groups = {Create.class})
    LocationDto location;
    @Builder.Default
    Boolean paid = false;
    @Builder.Default
    Long participantLimit = 0L;
    @Builder.Default
    Boolean requestModeration = true;
    @NotBlank(groups = {Create.class})
    @Size(min = 3, max = 120, groups = {Create.class})
    String title;
    UserStateAction stateAction;
}
