package ru.practicum.ewm_stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String app;

    @Column(nullable = false, length = 64)
    private String uri;

    @Column(nullable = false, length = 16)
    private String ip;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
