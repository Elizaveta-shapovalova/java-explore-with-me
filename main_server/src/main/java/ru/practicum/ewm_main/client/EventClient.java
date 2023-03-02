package ru.practicum.ewm_main.client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.practicum.ewm_main.constant.Constant.DATE_TIME_FORMATTER;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventClient {
    @Value("${app-name}")
    String app;
    static final String GET_URI = "/events/";
    static final String TIME_START = LocalDateTime.of(2023, 1, 1, 0, 0, 0).format(DATE_TIME_FORMATTER);
    static final String TIME_END = LocalDateTime.of(2024, 1, 1, 0, 0, 0).format(DATE_TIME_FORMATTER);

    final RestTemplate restTemplate;

    @Autowired
    public EventClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }


    public void createHit(HttpServletRequest request) {
        EndpointHit endpointHit = EndpointHit.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app(app)
                .timestamp(LocalDateTime.now().format(DATE_TIME_FORMATTER))
                .build();

        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(endpointHit, defaultHeaders());

        restTemplate.exchange("/hit", HttpMethod.POST, requestEntity, Void.class);
    }

    public List<ViewStats> getViews(Set<Long> eventIds) {
        List<String> uris = new ArrayList<>();
        eventIds.forEach(e -> uris.add(GET_URI + e));

        Map<String, Object> parameters = Map.of(
                "uris", uris,
                "start", TIME_START,
                "end", TIME_END);

        ResponseEntity<List<ViewStats>> response =
                restTemplate.exchange("/stats?start={start}&end={end}&uris={uris}", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        }, parameters);
        return response.getBody();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }
}
