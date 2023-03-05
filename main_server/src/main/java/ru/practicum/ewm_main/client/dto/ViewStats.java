package ru.practicum.ewm_main.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViewStats {
    String app;
    String uri;
    Long hits;
}
