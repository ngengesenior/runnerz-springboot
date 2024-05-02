package com.ngenge.runners.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Focus on loading only JDBC based components
 */
@JdbcTest
@Import(JdbcClientRunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcClientRunRepositoryTest {
    @Autowired
    JdbcClientRunRepository repository;

    @BeforeEach
    void setUp() {
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
        var runs = repository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repository.findById(1).get();
        assertEquals(5,run.miles());
        assertEquals("Monday Run",run.title());
    }

    @Test
    void shouldCreateValidRun() {
        var run = new Run(
                4,
                "Saturday Run",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusMinutes(30),
                9,
                Location.INDOOR,
                null);
        repository.create(run);
        assertEquals(3,repository.count());
    }

    @Test
    void shouldDeleteValidRun() {
        repository.delete(1);
        assertEquals(1,repository.count());
    }

}