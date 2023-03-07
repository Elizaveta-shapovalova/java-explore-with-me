package ru.practicum.ewm_main.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.request.model.Status;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    String created;
    Long event;
    Long id;
    Long requester;
    Status status;
    Boolean isPaid;
}
