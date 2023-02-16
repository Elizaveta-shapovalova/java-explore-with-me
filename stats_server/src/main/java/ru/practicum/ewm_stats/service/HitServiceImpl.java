package ru.practicum.ewm_stats.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.mapper.StatsMapper;
import ru.practicum.ewm_stats.model.Hit;
import ru.practicum.ewm_stats.repository.HitRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HitServiceImpl implements HitService {
    HitRepository hitRepository;

    @Transactional
    @Override
    public void create(Hit hit) {
        hitRepository.save(hit);
    }

    @Override
    public List<ViewStats> get(String start, String end, List<String> uris, Boolean uniq) {
        return uniq ? hitRepository.findDistinctViews(StatsMapper.getDateTime(start), StatsMapper.getDateTime(end), uris)
                : hitRepository.findViews(StatsMapper.getDateTime(start), StatsMapper.getDateTime(end), uris);
    }
}
