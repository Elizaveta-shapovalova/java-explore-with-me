package ru.practicum.ewm_main.request.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.event.enums.State;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;
import ru.practicum.ewm_main.exception.ValidationException;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.Status;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    UserRepository userRepository;
    EventRepository eventRepository;

    @Override
    public List<Request> getAll(Long userId) {
        checkUser(userId);
        return requestRepository.findAllByRequesterId(userId);
    }

    @Transactional
    @Override
    public Request create(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        if (requestRepository.findByEventIdAndRequesterId(eventId, userId) != null) {
            throw new ValidationException("Request already exist.");
        }
        if (event.getInitiator().equals(user)) {
            throw new ValidationException("Initiator can't add request to owen event.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Event not published yet.");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit()
                .equals(requestRepository.countRequestByEventIdAndStatusEquals(eventId, Status.CONFIRMED))) {
            throw new ValidationException("Event has reached participant limit");
        }
        Request request = Request.builder()
                .requester(user)
                .event(event)
                .build();
        if (!event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        return requestRepository.save(request);
    }

    @Transactional
    @Override
    public Request cancel(Long userId, Long requestId) {
        checkUser(userId);
        Request requestToCansel = checkRequest(requestId);
        requestToCansel.setStatus(Status.CANCELED);
        requestRepository.deleteById(requestId);
        return requestToCansel;
    }

    @Override
    public List<Request> getAllPrivate(Long userId, Long eventId) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can see requests");
        }
        return requestRepository.findAllByEventId(eventId);
    }

    @Transactional
    @Override
    public List<Request> updateStatusPrivate(Long userId, Long eventId, List<Long> requestIds, Status status) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        Long confirmedRequests = requestRepository.countRequestByEventIdAndStatusEquals(eventId, Status.CONFIRMED);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Only initiator can update requests");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(confirmedRequests)) {
            throw new ValidationException("Event has reached participant limit");
        }

        List<Request> requestToSend = new ArrayList<>();
        List<Request> requests = requestRepository.findAllByIdIn(requestIds);

        if (requests.size() != requestIds.size()) {
            throw new ObjectNotFoundException(String.format("Events with ids=%s was not found", requestIds));
        }

        for (Request request : requests) {
            if (!request.getEvent().getId().equals(eventId)) {
                throw new ValidationException(String.format("Event and request with id=%d event don't match", request.getId()));
            }
            if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
                requestToSend.add(request);
            } else {
                if (!request.getStatus().equals(Status.PENDING)) {
                    throw new ValidationException(String.format("Request with id=%d not pending", request.getId()));
                }
                if (status.equals(Status.CONFIRMED) && event.getParticipantLimit() > confirmedRequests) {
                    request.setStatus(Status.CONFIRMED);
                    requestToSend.add(request);
                } else {
                    requestToSend.add(request);
                    request.setStatus(Status.REJECTED);
                }
            }
        }

        return requestToSend;
    }

    private Request checkRequest(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Request with id=%d was not found", requestId)));
    }

    private User checkUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("User with id=%d was not found", userId)));
    }

    private Event checkEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Event with id=%d was not found", eventId)));
    }
}
