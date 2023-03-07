package ru.practicum.ewm_main.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm_main.paypal.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(" SELECT p " +
            "FROM Payment p " +
            "JOIN FETCH p.request " +
            "WHERE p.token = (?1) ")
    Optional<Payment> findByToken(String orderId);
}
