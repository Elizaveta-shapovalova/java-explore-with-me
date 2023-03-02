package ru.practicum.ewm_main.request.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.request.dto.RequestDto;
import ru.practicum.ewm_main.request.mapper.RequestMapper;
import ru.practicum.ewm_main.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PrivateRequestController {
    RequestService requestService;

    @GetMapping
    public List<RequestDto> getAll(@PathVariable Long userId) {
        return RequestMapper.toListRequestDto(requestService.getAll(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        return RequestMapper.toRequestDto(requestService.create(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        return RequestMapper.toRequestDto(requestService.cancel(userId, requestId));
    }
}
