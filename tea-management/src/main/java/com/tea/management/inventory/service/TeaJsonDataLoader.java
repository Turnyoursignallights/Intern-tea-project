package com.tea.management.inventory.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.repository.TeaRepositoryJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Automatic Tea Data Initializer:
 * This component automatically loads initial tea inventory data from a JSON file
 * into the database when the application starts for the first time. It functions as a
 * data bootstrapper for the tea inventory system.
 */
@Component
public class TeaJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(TeaJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final TeaRepositoryJpa teajson;

    public TeaJsonDataLoader(ObjectMapper objectMapper, TeaRepositoryJpa teajson) {
        this.objectMapper = objectMapper;
        this.teajson = teajson;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teajson.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/teas.json")) {
                List<Tea> teaList = objectMapper.readValue(inputStream, new TypeReference<List<Tea>>() {
                });
                log.info("Reading {} runs from JSON data and saving to collection.", teaList.size());
                teajson.saveAll(teaList);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because....");
        }
    }

}
