package ru.practicum.ewm_stats.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm_stats.dto.ViewStats;
import ru.practicum.ewm_stats.model.Hit;
import ru.practicum.ewm_stats.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class HitServiceImplTest {
    @Mock
    HitRepository hitRepository;

    @InjectMocks
    HitServiceImpl hitService;
    final ViewStats viewStats = new ViewStats("testApp", "testUri", 1L);


    @Test
    void create_whenInvoked_thenVerifyMethod() {
        hitService.create(new Hit(1L, "testApp", "testUri", "testIp", LocalDateTime.now()));

        verify(hitRepository).save(any());
    }

    @Test
    void get_whenUniqueFalse_thenReturnCollectionViewStats() {
        when(hitRepository.findViews(any(), any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get("2020-02-02 02:02:02", "2020-02-02 02:02:02", List.of(),
                Boolean.FALSE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }

    @Test
    void get_whenUrisEmpty_thenReturnEmptyCollectionViewStats() {
        when(hitRepository.findViews(any(), any(), any())).thenReturn(List.of());

        List<ViewStats> actualViewStats = hitService.get("2020-02-02 02:02:02", "2020-02-02 02:02:02", List.of(),
                Boolean.FALSE);

        assertTrue(actualViewStats.isEmpty());
    }

    @Test
    void get_whenUniqueTrue_thenReturnCollectionViewStats() {
        when(hitRepository.findDistinctViews(any(), any(), any())).thenReturn(List.of(viewStats));

        List<ViewStats> actualViewStats = hitService.get("2020-02-02 02:02:02", "2020-02-02 02:02:02", List.of(),
                Boolean.TRUE);

        assertFalse(actualViewStats.isEmpty());
        assertEquals(1, actualViewStats.size());
        assertEquals(List.of(viewStats), actualViewStats);
    }
}
