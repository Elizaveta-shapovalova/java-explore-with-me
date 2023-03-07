package ru.practicum.ewm_main.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_main.request.dto.RequestConfirmedDto;
import ru.practicum.ewm_main.request.dto.RequestDto;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.Status;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm_main.constant.Constant.DATE_TIME_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .created(request.getCreated().format(DATE_TIME_FORMATTER))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .isPaid(request.getIsPaid())
                .build();
    }

    public static List<RequestDto> toListRequestDto(List<Request> requests) {
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    public static RequestConfirmedDto toRequestConfirmedDto(List<Request> requests) {
        return RequestConfirmedDto.builder()
                .confirmedRequests(requests.stream().filter(request -> request.getStatus().equals(Status.CONFIRMED))
                        .map(RequestMapper::toRequestDto).collect(Collectors.toList()))
                .rejectedRequests(requests.stream().filter(request -> request.getStatus().equals(Status.REJECTED))
                        .map(RequestMapper::toRequestDto).collect(Collectors.toList()))
                .build();
    }
}
