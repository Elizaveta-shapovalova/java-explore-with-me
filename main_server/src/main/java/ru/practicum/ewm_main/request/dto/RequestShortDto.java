package ru.practicum.ewm_main.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.request.model.Status;
import ru.practicum.ewm_main.validation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestShortDto {
    @NotBlank(groups = {Update.class})
    List<Long> requestIds;
    @NotNull(groups = {Update.class})
    Status status;
}
