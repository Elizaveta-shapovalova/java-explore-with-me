package ru.practicum.ewm_main.paypal.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm_main.request.model.Request;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "token", length = 32, nullable = false)
    String token;
    @Column(name = "status", length = 32, nullable = false)
    String status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    Request request;
}
