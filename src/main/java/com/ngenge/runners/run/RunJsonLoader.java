package com.ngenge.runners.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RunJsonLoader.class);
    private final ObjectMapper objectMapper;
    private final JdbcClientRunRepository runRepository;

    // Run repo will be injected
    public RunJsonLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (runRepository.count() == 0) {
            try (InputStream stream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(stream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to memory.", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (IOException ex) {
                throw new RuntimeException("Failed to read JSON data", ex);
            }
        } else {
            log.info("Not loading runs romJSOn since data is already available");
        }
    }
}
