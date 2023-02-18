package ru.practicum.ewm_stats.service;

import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.model.App;
import ru.practicum.ewm_stats.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {

    void create(Hit hit, App app);

    List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean uniq);
}


