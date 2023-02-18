package ru.practicum.ewm_stats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.ewm_stats.dto.EndpointHit;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.service.HitService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatsController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class StatsControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    HitService hitService;

    @SneakyThrows
    @Test
    void create_whenInvoked_thenReturnResponseStatusCreated() {
        mvc.perform(MockMvcRequestBuilders.post("/hit")
                        .content(mapper.writeValueAsString(new EndpointHit("testApp", "testUri", "testIp", "2020-03-03 03:03:03")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(hitService).create(any(), any());
    }

    @SneakyThrows
    @Test
    void get_whenInvoked_thenReturnResponseStatusOkWithCollectionViewStats() {
        when(hitService.get(any(), any(), any(), any())).thenReturn(List.of(new ViewStats("testApp", "testUri", 5L)));

        mvc.perform(MockMvcRequestBuilders.get("/stats")
                        .param("start", "2020-02-02 02:02:02")
                        .param("end", "2020-02-02 02:02:02")
                        .param("uris", List.of().toString())
                        .param("unique", "FALSE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].app").value("testApp"))
                .andExpect(jsonPath("$.[0].uri").value("testUri"))
                .andExpect(jsonPath("$.[0].hits").value(5L));
    }
}
