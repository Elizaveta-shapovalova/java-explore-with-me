package ru.practicum.ewm_stats.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.model.App;
import ru.practicum.ewm_stats.model.Hit;
import ru.practicum.ewm_stats.repository.AppRepository;
import ru.practicum.ewm_stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HitServiceImpl implements HitService {
    HitRepository hitRepository;
    AppRepository appRepository;

    @Transactional
    @Override
    public void create(Hit hit, App app) {
        hit.setApp(Objects.requireNonNullElseGet(appRepository.findByName(app.getName()), () -> appRepository.save(app)));
        hitRepository.save(hit);
    }

    @Override
    public List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean uniq) {
        if (uris == null || uris.isEmpty()) {
            return uniq ? hitRepository.findAllDistinctViews(start, end)
                    : hitRepository.findAllViews(start, end);
        } else {
            return uniq ? hitRepository.findDistinctViews(start, end, uris)
                    : hitRepository.findViews(start, end, uris);
        }
    }
}
