package ru.practicum.ewm_main.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    List<Event> findAllByCategoryId(Long catId);

    Optional<Event> findByIdAndStateEquals(Long id, State state);

    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);
}




