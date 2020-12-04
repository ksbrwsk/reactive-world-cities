package de.ksbrwsk.citiies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Log4j2
@RequiredArgsConstructor
public class DatabaseInitializr {

    private final CityRepository cityRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void process() throws IOException {
        boolean initialized = this.isDatabaseInitialized();
        if (!initialized) {
            this.processData();
        } else {
            log.info("Database already initialized.");
        }
    }

    private void processData() throws IOException {
        log.info("Database not initialized, persisting data...");
        Resource resource = new ClassPathResource("/world-cities_csv.csv");
        List<String> strings = FileUtils.readLines(resource.getFile(), StandardCharsets.UTF_8);
        List<City> cities = strings
                .stream()
                .skip(1) // headers
                //.limit(5) // first 5 lines only
                .map(this::splitCityTuple)
                .map(this::createCity)
                .collect(toList());

        // TODO: Eliminate blocking call
        this.cityRepository
                .deleteAll()
                .thenMany(this.cityRepository.saveAll(cities))
                .then(this.cityRepository.count())
                .onErrorMap(ex -> new RuntimeException("Error persisting data", ex))
                .block();
        log.info("Data successfully persisted");

    }

    private boolean isDatabaseInitialized() {
        log.info("Check database initialised");
        Long count = this.cityRepository.count().block();
        return count > 0;
    }

    private City createCity(String[] cityTuple) {
        return new City(null, cityTuple[0], cityTuple[1], cityTuple[2], cityTuple[3]);
    }

    private String[] splitCityTuple(String s) {
        return s.split(",");
    }
}
