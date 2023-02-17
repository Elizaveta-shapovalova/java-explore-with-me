package ru.practicum.ewm_stats.service;

import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.model.Hit;

import java.util.List;

public interface HitService {

    void create(Hit hit);

    List<ViewStats> get(String start, String end, List<String> uris, Boolean uniq);
}


