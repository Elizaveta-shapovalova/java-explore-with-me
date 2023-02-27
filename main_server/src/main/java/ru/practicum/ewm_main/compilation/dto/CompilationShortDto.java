package ru.practicum.ewm_main.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.validation.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationShortDto {
    List<Long> events;
    @NotNull
    @Builder.Default
    Boolean pinned = false;
    @NotBlank(groups = {Create.class}, message = "Field: title. Error: must not be blank. Value: null")
    String title;
}
