package ru.practicum.ewm_main.paypal.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_main.paypal.dto.PaymentOrder;
import ru.practicum.ewm_main.paypal.model.Payment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMapper {
    public static Payment toPayment(PaymentOrder paymentOrder) {
        return Payment.builder()
                .status(paymentOrder.getStatus())
                .token(paymentOrder.getPayId())
                .build();
    }
}
