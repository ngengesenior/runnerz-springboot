package com.ngenge.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntegrationTest {
    @LocalServerPort
    int randomPort;

    RestClient restClient;
    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:"+ randomPort);
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = restClient.get()
                .uri("api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        assertTrue(runs.size() > 0);
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);
        assertAll(
                ()-> assertEquals(1,run.id()),
                ()-> assertEquals("Noon Run",run.title()),
                ()-> assertEquals("2024-02-20T06:05",run.startedOn().toString()),
                ()-> assertEquals("2024-02-20T10:27",run.completedOn().toString()),
                ()-> assertEquals(24,run.miles()),
                ()-> assertEquals("INDOOR",run.location().toString())

        );
        /**
         * {
         *       "id": 1,
         *       "title": "Noon Run",
         *       "startedOn": "2024-02-20T06:05:00.000000",
         *       "completedOn": "2024-02-20T10:27:00.000000",
         *       "miles": 24,
         *       "location": "INDOOR"
         *     }
         */



    }

    @Test
    void shouldCreateNewRun() {
        var run = new Run(
                22,
                "test",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(3),
                5,
                Location.INDOOR,
                null
        );

        ResponseEntity<Void> newRun = restClient
                .post()
                .uri("/api/runs")
                .body(run)
                .retrieve()
                .toBodilessEntity();
        assertEquals(201,newRun.getStatusCode().value());

    }

}