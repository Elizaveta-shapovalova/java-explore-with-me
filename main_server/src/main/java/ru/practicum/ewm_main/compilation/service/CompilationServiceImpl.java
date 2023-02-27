package ru.practicum.ewm_main.compilation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.compilation.repository.CompilationRepository;
import ru.practicum.ewm_main.event.model.Event;
import ru.practicum.ewm_main.event.repository.EventRepository;
import ru.practicum.ewm_main.exception.ObjectNotFoundException;

import java.util.HashSet;
import java.util.List;

import static ru.practicum.ewm_main.constant.Constant.SORT_BY_ASC;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompilationServiceImpl implements CompilationService {

    CompilationRepository compilationRepository;
    EventRepository eventRepository;

    @Transactional
    @Override
    public Compilation create(Compilation compilation, List<Long> eventIds) {
        if (eventIds != null && !eventIds.isEmpty()) {
            compilation.setEvents(new HashSet<>(getEventsAndCheck(eventIds)));
        }
        return compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        compilationRepository.delete(getById(id));
    }

    @Transactional
    @Override
    public Compilation update(Long id, Compilation compilation, List<Long> eventIds) {
        Compilation compilationToUpdate = getById(id);
        if (eventIds != null && !eventIds.isEmpty()) {
            compilationToUpdate.setEvents(new HashSet<>(getEventsAndCheck(eventIds)));
        }
        if (compilation.getTitle() != null && !compilation.getTitle().isBlank()) {
            compilationToUpdate.setTitle(compilation.getTitle());
        }
        if (compilation.getPinned() != null) {
            compilationToUpdate.setPinned(compilation.getPinned());
        }
        return compilationToUpdate;
    }

    @Override
    public Page<Compilation> getAll(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, SORT_BY_ASC);
        return pinned != null ? compilationRepository.findAllByPinnedEquals(pinned, pageable)
                : compilationRepository.findAll(pageable);
    }

    @Override
    public Compilation getById(Long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Compilation with id=%d was not found", id)));
    }

    private List<Event> getEventsAndCheck(List<Long> eventIds) {
        List<Event> events = eventRepository.findAllById(eventIds);
        if (events.isEmpty() || events.size() != eventIds.size()) {
            throw new ObjectNotFoundException("Events in compilation doesn't exist.");
        } else {
            return events;
        }
    }
}
