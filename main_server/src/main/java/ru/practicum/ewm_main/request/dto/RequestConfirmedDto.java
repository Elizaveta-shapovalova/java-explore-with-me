package ru.practicum.ewm_main.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestConfirmedDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
