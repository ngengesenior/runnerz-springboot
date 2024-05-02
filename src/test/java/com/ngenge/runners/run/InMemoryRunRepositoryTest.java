package com.ngenge.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {
    InMemoryRunRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRunRepository();
        repository.create(new Run(
                1,
                "Monday Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                5,
                Location.INDOOR,
                null

        ));

        repository.create(new Run(
                2,
                "Tuesday Run",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusMinutes(50),
                9,
                Location.INDOOR,
                null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2,runs.size(),"Should have returned 2 runs");
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repository.findById(1).get();
        assertEquals(5,run.miles());
        assertEquals("Monday Run",run.title());
    }

    @Test
    void shouldNotFindRunWithInvalidId() {
        RunNotFoundException  notFoundException = assertThrows(
                RunNotFoundException.class,
                ()-> repository.findById(3).get()
        );
        assertEquals("Run Not Found",notFoundException.getMessage());
    }

    @Test
    void shouldValidCreateRun() {
        Run run = new Run(
                3,
                "Saturday Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(40),
                12,
                Location.INDOOR,
                null);

        repository.create(run);
        List<Run> runs = repository.findAll();
        assertEquals(3,runs.size());
    }

    @Test
    void shouldDeleteValidRun() {
        repository.delete(1);
        assertEquals(1,repository.count(), "There should be just one item in memory");
    }

}