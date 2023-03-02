package ru.practicum.ewm_stats.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_stats.dto.EndpointHit;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.mapper.StatsMapper;
import ru.practicum.ewm_stats.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatsController {
    HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody EndpointHit endpointHit) {
        hitService.create(StatsMapper.toHit(endpointHit), StatsMapper.toApp(endpointHit.getApp()));
    }

    @GetMapping("/stats")
    public List<ViewStats> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                               @RequestParam(required = false) List<String> uris,
                               @RequestParam(defaultValue = "false") Boolean uniq) {
        return hitService.get(start, end, uris, uniq);
    }
}
