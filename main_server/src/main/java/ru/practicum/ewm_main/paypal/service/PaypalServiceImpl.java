package ru.practicum.ewm_main.paypal.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;
import ru.practicum.ewm_main.exception.ValidationException;
import ru.practicum.ewm_main.paypal.client.PaymentClient;
import ru.practicum.ewm_main.paypal.dto.PaymentOrder;
import ru.practicum.ewm_main.paypal.mapper.PaymentMapper;
import ru.practicum.ewm_main.paypal.model.Payment;
import ru.practicum.ewm_main.paypal.repository.PaymentRepository;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.Status;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaypalServiceImpl implements PaypalService {
    RequestRepository requestRepository;
    UserRepository userRepository;
    PaymentRepository paymentRepository;
    PaymentClient paymentClient;

    @Transactional
    @Override
    public PaymentOrder create(Long userId, Long requestId) {
        checkUser(userId);
        Request request = checkRequest(requestId);

        if (!request.getStatus().equals(Status.CONFIRMED)) {
            throw new ValidationException("Request doesn't confirmed yet.");
        }
        if (!userId.equals(request.getRequester().getId())) {
            throw new ValidationException("Only requester can pay for request.");
        }
        PaymentOrder paymentOrder = paymentClient.createOrder(request.getEvent().getPrice());
        Payment payment = PaymentMapper.toPayment(paymentOrder);
        payment.setRequest(request);
        paymentRepository.save(payment);
        return paymentOrder;
    }

    @Transactional
    @Override
    public Request confirm(String token) {
        Payment payment = paymentRepository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Payment with token=%S was not found", token)));
        if (!payment.getStatus().equals("CREATED")) {
            throw new ValidationException("Request has payed.");
        }
        payment.setStatus("APPROVED");
        Request request = payment.getRequest();
        request.setIsPaid(true);
        return request;
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id=%d was not found", userId)));
    }

    private Request checkRequest(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Request with id=%d was not found", requestId)));
    }
}
