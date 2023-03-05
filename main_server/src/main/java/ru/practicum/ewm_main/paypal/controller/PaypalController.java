package ru.practicum.ewm_main.paypal.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.paypal.dto.PaymentOrder;
import ru.practicum.ewm_main.paypal.service.PaypalService;
import ru.practicum.ewm_main.request.dto.RequestDto;
import ru.practicum.ewm_main.request.mapper.RequestMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pay")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaypalController {
    PaypalService paypalService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests/{requestId}")
    public PaymentOrder create(@PathVariable Long userId, @PathVariable Long requestId) {
        return paypalService.create(userId, requestId);
    }

    @PostMapping("/success")
    public RequestDto confirm(@RequestParam("token") String token) {
        return RequestMapper.toRequestDto(paypalService.confirm(token));
    }
}
