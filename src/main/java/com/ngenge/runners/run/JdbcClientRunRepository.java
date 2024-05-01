package com.ngenge.runners.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {
    private static final Logger log = LoggerFactory.getILoggerFactory().getLogger(JdbcClientRunRepository.class.getName());
    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Run> findAll() {
        return jdbcClient.sql("select * from Run")
                .query(Run.class)
                .list();
    }

    Optional<Run> findById(Integer id) {
        return jdbcClient.sql("select id,title,started_on,completed_on,miles,location FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location) VALUES (?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();
        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ? ")
                .param(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM run WHERE id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete run " + id);

    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM run")
                .query()
                .listOfRows()
                .size();
    }

    List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }


    /*@PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday", LocalDateTime.of(2024, 4, 22, 7, 0), LocalDateTime.of(2024, 04, 22, 9, 0), 8, Location.INDOOR));
        runs.add(new Run(2, "Tuesday", LocalDateTime.of(2024, 4, 23, 10, 0), LocalDateTime.of(2024, 04, 23, 12, 0), 5, Location.OUTDOOR));
        runs.add(new Run(3, "Wednesday", LocalDateTime.of(2024, 4, 24, 15, 0), LocalDateTime.of(2024, 04, 24, 17, 0), 7, Location.INDOOR));
        runs.add(new Run(4, "Thursday", LocalDateTime.of(2024, 4, 25, 8, 30), LocalDateTime.of(2024, 04, 25, 11, 0), 10, Location.OUTDOOR));
        runs.add(new Run(5, "Friday", LocalDateTime.of(2024, 4, 26, 6, 0), LocalDateTime.of(2024, 04, 26, 8, 0), 6, Location.INDOOR));
    }*/
}
