package ru.practicum.ewm_main.paypal.service;

import ru.practicum.ewm_main.paypal.dto.PaymentOrder;
import ru.practicum.ewm_main.request.model.Request;

public interface PaypalService {
    PaymentOrder create(Long userId, Long requestId);

    Request confirm(String token);
}
