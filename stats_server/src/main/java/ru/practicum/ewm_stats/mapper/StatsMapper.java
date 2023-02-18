package ru.practicum.ewm_stats.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm_stats.dto.EndpointHit;
import ru.practicum.ewm_stats.model.App;
import ru.practicum.ewm_stats.model.Hit;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsMapper {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Hit toHit(EndpointHit endpointHit) {
        return Hit.builder()
                .ip(endpointHit.getIp())
                .uri(endpointHit.getUri())
                .timestamp(getDateTime(endpointHit.getTimestamp()))
                .build();
    }

    public static App toApp(String appName) {
        return App.builder()
                .name(appName)
                .build();
    }

    public static LocalDateTime getDateTime(String dateTime) {
        dateTime = URLDecoder.decode(dateTime, StandardCharsets.UTF_8);
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}
