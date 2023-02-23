package ru.practicum.ewm_main.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.validation.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserRequest {
    @NotBlank(groups = {Create.class}, message = "Field: email. Error: must not be blank. Value: null")
    @Email(groups = {Create.class}, message = "Field: email. Error: must contains @ symbol")
    String email;
    @NotBlank(groups = {Create.class}, message = "Field: name. Error: must not be blank. Value: null")
    String name;
}
