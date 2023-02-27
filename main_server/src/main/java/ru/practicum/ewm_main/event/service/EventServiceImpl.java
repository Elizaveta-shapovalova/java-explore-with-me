package ru.practicum.ewm_main.event.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.category.model.Category;
import ru.practicum.ewm_main.category.repository.CategoryRepository;
import ru.practicum.ewm_main.event.enums.AdminStateAction;
import ru.practicum.ewm_main.event.enums.SortType;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.event.enums.UserStateAction;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.model.QEvent;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.event.repository.LocationRepository;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;
import ru.practicum.ewm_main.exception.ValidationException;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.Status;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static ru.practicum.ewm_main.constant.Constant.SORT_BY_ASC;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    LocationRepository locationRepository;
    static Sort SORT_BY_VIEWS = Sort.by(Sort.Direction.DESC, "views");
    static Sort SORT_BY_EVENT_DATE = Sort.by(Sort.Direction.ASC, "eventDate");
    private final RequestRepository requestRepository;

    @Override
    public List<Event> getAllPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable, SortType sort, Integer from, Integer size) {

        QEvent event = QEvent.event;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(event.state.eq(State.PUBLISHED));
        if (text != null && !text.isBlank()) {
            builder.and(event.annotation.containsIgnoreCase(text)
                    .or(event.description.containsIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            builder.and(event.category.id.in(categories));
        }
        if (paid != null) {
            builder.and(event.paid.eq(paid));
        }
        if (rangeStart != null) {
            builder.and(event.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(event.eventDate.before(rangeEnd));
        }
        if (rangeStart == null && rangeEnd == null) {
            builder.and(event.eventDate.after(LocalDateTime.now()));
        }

        Pageable pageable;

        if (sort != null) {
            if (sort.equals(SortType.VIEWS)) {
                pageable = PageRequest.of(from / size, size, SORT_BY_VIEWS);
            } else {
                pageable = PageRequest.of(from / size, size, SORT_BY_EVENT_DATE);
            }
        } else {
            pageable = PageRequest.of(from / size, size, SORT_BY_ASC);
        }

        List<Event> events = eventRepository.findAll(builder, pageable).stream().collect(Collectors.toList());
        loadConfirmedRequests(events);

        if (onlyAvailable) {
            return events.stream().filter(e -> e.getParticipantLimit() > e.getConfirmedRequests()).collect(Collectors.toList());
        } else {
            return events;
        }
    }

    @Override
    public Event getByIdPublic(Long eventId) {
        Event event = eventRepository.findByIdAndStateEquals(eventId, State.PUBLISHED)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Event with id=%d was not found", eventId)));
        loadConfirmedRequests(List.of(event));
        return event;
    }

    @Override
    public List<Event> getAllAdmin(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Integer from, Integer size) {

        Pageable pageable = PageRequest.of(from / size, size, SORT_BY_ASC);
        QEvent event = QEvent.event;
        BooleanBuilder builder = new BooleanBuilder();

        if (rangeStart == null && rangeEnd == null) {
            builder.and(event.eventDate.after(LocalDateTime.now()));
        }
        if (rangeStart != null) {
            builder.and(event.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(event.eventDate.before(rangeEnd));
        }
        if (users != null && !users.isEmpty()) {
            builder.and(event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            builder.and(event.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            builder.and(event.category.id.in(categories));
        }
        List<Event> events = eventRepository.findAll(builder, pageable).stream().collect(Collectors.toList());
        loadConfirmedRequests(events);
        return events;
    }

    @Transactional
    @Override
    public Event updateAdmin(Long eventId, Long categoryId, AdminStateAction stateAction, Event event) {
        Event eventToUpdate = checkEvent(eventId);
        if (!eventToUpdate.getState().equals(State.PENDING)) {
            throw new ValidationException(String.format("Cannot publish the event because it's not in the right state: %S",
                    eventToUpdate.getState()));
        }
        if (stateAction != null && stateAction.equals(AdminStateAction.PUBLISH_EVENT)) {
            if (eventToUpdate.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ValidationException(String.format("Cannot publish the event because it's not in the right time: %s",
                        eventToUpdate.getEventDate()));
            }
            eventToUpdate.setPublishedOn(LocalDateTime.now());
            eventToUpdate.setState(State.PUBLISHED);
            updateFields(categoryId, eventToUpdate, event);
        } else if (stateAction != null && stateAction.equals(AdminStateAction.REJECT_EVENT)) {
            eventToUpdate.setState(State.CANCELED);
            updateFields(categoryId, eventToUpdate, event);
        } else {
            updateFields(categoryId, eventToUpdate, event);
        }
        return eventToUpdate;
    }

    @Override
    public List<Event> getAllPrivate(Long userId, Integer from, Integer size) {
        checkUser(userId);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size, SORT_BY_ASC))
                .stream().collect(Collectors.toList());
        loadConfirmedRequests(events);
        return events;
    }

    @Transactional
    @Override
    public Event create(Long userId, Long category, Event event) {
        checkEventTime(event.getEventDate());
        event.setInitiator(checkUser(userId));
        event.setCategory(checkCategory(category));
        event.setLocation(locationRepository.save(event.getLocation()));
        return eventRepository.save(event);
    }

    @Override
    public Event getByIdPrivate(Long userId, Long eventId) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can get full information or update event");
        }
        loadConfirmedRequests(List.of(event));
        return event;
    }

    @Transactional
    @Override
    public Event updatePrivate(Long userId, Long eventId, Long category, UserStateAction stateAction, Event event) {
        Event eventToUpdate = getByIdPrivate(userId, eventId);
        if (eventToUpdate.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Only pending or canceled events can be changed");
        }
        if (stateAction != null && stateAction.equals(UserStateAction.CANCEL_REVIEW)) {
            eventToUpdate.setState(State.CANCELED);
            updateFields(category, eventToUpdate, event);
        } else {
            eventToUpdate.setState(State.PENDING);
            updateFields(category, eventToUpdate, event);
        }
        return eventToUpdate;
    }

    private void updateFields(Long categoryId, Event eventToUpdate, Event event) {
        if (categoryId != null) {
            Category categoryToUpdate = checkCategory(categoryId);
            eventToUpdate.setCategory(categoryToUpdate);
        }
        if (event.getAnnotation() != null && !event.getAnnotation().isBlank()) {
            eventToUpdate.setAnnotation(event.getAnnotation());
        }
        if (event.getDescription() != null && !event.getDescription().isBlank()) {
            eventToUpdate.setDescription(event.getDescription());
        }
        if (event.getLocation() != null) {
            eventToUpdate.setLocation(locationRepository.save(event.getLocation()));
        }
        if (event.getPaid() != null) {
            eventToUpdate.setPaid(event.getPaid());
        }
        if (event.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            eventToUpdate.setRequestModeration(event.getRequestModeration());
        }
        if (event.getTitle() != null && !event.getTitle().isBlank()) {
            eventToUpdate.setTitle(event.getTitle());
        }
        if (event.getEventDate() != null) {
            checkEventTime(event.getEventDate());
            eventToUpdate.setEventDate(event.getEventDate());
        }
    }

    private void loadConfirmedRequests(List<Event> events) {
        Map<Event, Set<Request>> requests = requestRepository.findAllByEventInAndStatusEquals(events, Status.CONFIRMED)
                .stream()
                .collect(groupingBy(Request::getEvent, toSet()));

        events.forEach(event -> event.setConfirmedRequests((long) requests.values().size()));
    }

    private Category checkCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Category with id=%d was not found", categoryId)));
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id=%d was not found", userId)));
    }

    private Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Event with id=%d was not found", eventId)));
    }

    private void checkEventTime(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException(String.format("EventDate cannot be earlier than two hours from the current moment. Value: %s", eventDate));
        }
    }
}
