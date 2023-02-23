package ru.practicum.ewm_main.category.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.validation.Create;
import ru.practicum.ewm_main.validation.Update;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryShortDto {
    @NotBlank(groups = {Create.class, Update.class}, message = "Field: name. Error: must not be blank. Value: null")
    String name;
}
