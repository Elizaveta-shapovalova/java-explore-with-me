package ru.practicum.ewm_stats.dto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class EndpointHitTest {
    @Autowired
    private JacksonTester<EndpointHit> jacksonTester;

    @SneakyThrows
    @Test
    void testSerialize() {
        EndpointHit endpointHit = new EndpointHit("testApp", "testUri", "testIp", "2020-03-03 03:03:03");

        JsonContent<EndpointHit> result = jacksonTester.write(endpointHit);

        assertThat(result).extractingJsonPathStringValue("$.app").isEqualTo("testApp");
        assertThat(result).extractingJsonPathStringValue("$.uri").isEqualTo("testUri");
        assertThat(result).extractingJsonPathStringValue("$.ip").isEqualTo("testIp");
        assertThat(result).extractingJsonPathStringValue("$.timestamp").isNotBlank();
    }
}
