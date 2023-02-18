package ru.practicum.ewm_stats.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    App app;

    @Column(nullable = false, length = 64)
    String uri;

    @Column(nullable = false, length = 16)
    String ip;

    @Column(nullable = false)
    LocalDateTime timestamp;
}
