package ru.practicum.ewm_main.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.annotation.StartTimeValid;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.validation.Create;
import ru.practicum.ewm_main.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@StartTimeValid(groups = {Create.class, Update.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    @NotBlank(groups = {Create.class})
    String annotation;
    @NotNull(groups = {Create.class})
    Long category;
    @NotBlank(groups = {Create.class})
    String description;
    @NotNull(groups = {Create.class})
    String eventDate;
    @NotNull(groups = {Create.class})
    LocationDto locationDto;
    @Builder.Default
    Boolean paid = false;
    @Builder.Default
    Long participantLimit = 0L;
    @Builder.Default
    Boolean requestModeration = true;
    @NotBlank(groups = {Create.class})
    String title;
    UserStateAction stateAction;
}
