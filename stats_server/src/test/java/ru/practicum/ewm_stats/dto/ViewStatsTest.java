package ru.practicum.ewm_stats.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ViewStatsTest {
    @Autowired
    private JacksonTester<ViewStats> jacksonTester;

    @SneakyThrows
    @Test
    void testSerialize() {
        ViewStats viewStats = new ViewStats("testApp", "testUri", 5L);

        JsonContent<ViewStats> result = jacksonTester.write(viewStats);

        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo("testApp");
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo("testUri");
        assertThat(result).extractingJsonPathNumberValue("$.hits").isEqualTo(5);
    }
}
