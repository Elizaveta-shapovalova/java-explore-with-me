package ru.practicum.ewm_main.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static ru.practicum.ewm_main.constant.Constant.DATE_TIME_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeMapper {
    public static LocalDateTime getDateTime(String dateTime) {
        dateTime = URLDecoder.decode(dateTime, StandardCharsets.UTF_8);
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}
