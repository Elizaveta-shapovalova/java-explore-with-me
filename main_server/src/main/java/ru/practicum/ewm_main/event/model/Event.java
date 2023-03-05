package ru.practicum.ewm_main.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    @Length(min = 20, max = 2000)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;
    @CreatedDate
    @Column(name = "created_on")
    LocalDateTime createdOn;
    @Length(min = 20, max = 7000)
    String description;
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    Location location;
    @Column(nullable = false)
    Boolean paid;
    @Column(nullable = false)
    Double price; // цена мероприятия
    @Column(name = "participant_limit", nullable = false)
    Long participantLimit;
    @Column(name = "published_on")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    State state;
    @Column(nullable = false)
    @Length(min = 3, max = 120)
    String title;
    @Transient
    Long views = 0L;
    @Transient
    Long confirmedRequests;
}
