package ru.practicum.ewm_stats.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.model.App;
import ru.practicum.ewm_stats.model.Hit;
import ru.practicum.ewm_stats.repository.AppRepository;
import ru.practicum.ewm_stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class HitServiceImplTest {
    @Mock
    HitRepository hitRepository;
    @Mock
    AppRepository appRepository;

    @InjectMocks
    HitServiceImpl hitService;
    final ViewStats viewStats = new ViewStats("testApp", "testUri", 1L);
    final LocalDateTime testTime = LocalDateTime.of(2020, 2, 2, 2, 2, 2);
    final Hit hit = Hit.builder()
            .ip("testIp")
            .uri("testUri")
            .timestamp(testTime)
            .build();
    final App app = App.builder().name("testApp").build();


    @Test
    void create_whenInvoked_thenVerifyMethod() {
        when(appRepository.findByName(any())).thenReturn(null);
        when(appRepository.save(any())).thenReturn(app);
        hitService.create(hit, app);

        verify(hitRepository).save(any());
        verify(appRepository).save(any());
    }

    @Test
    void get_whenUniqueFalse_thenReturnCollectionViewStats() {
        when(hitRepository.findViews(any(), any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get(testTime, testTime, List.of("testUris"),
                Boolean.FALSE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }

    @Test
    void get_whenUniqueTrue_thenReturnCollectionViewStats() {
        when(hitRepository.findDistinctViews(any(), any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get(testTime, testTime, List.of("testUris"),
                Boolean.TRUE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }

    @Test
    void get_whenUniqueTrueAndUrisEmpty_thenReturnCollectionViewStats() {
        when(hitRepository.findAllDistinctViews(any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get(testTime, testTime, List.of(),
                Boolean.TRUE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }

    @Test
    void get_whenUniqueFalseAndUrisEmpty_thenReturnCollectionViewStats() {
        when(hitRepository.findAllViews(any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get(testTime, testTime, List.of(),
                Boolean.FALSE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }
}
