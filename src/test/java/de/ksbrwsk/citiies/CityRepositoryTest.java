package de.ksbrwsk.citiies;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@Log4j2
class CityRepositoryTest {

    @Autowired
    CityRepository worldCityRepository;

    @Test
    void allByName() {
        var count = this.worldCityRepository
                .findAllByName("Köln")
                .count()
                .block();
        assertEquals(1, count);
    }

    @Test
    void allByCountry() {
        var count = this.worldCityRepository
                .findAllByCountry("Germany")
                .count()
                .block();
        assertEquals(1055, count);
    }

    @Test
    void allBySubcountry() {
        var count = this.worldCityRepository
                .findAllBySubcountry("North Rhine-Westphalia")
                .count()
                .block();
        assertEquals(280, count);
    }

    @Test
    @Disabled
    void persist() {
        Flux<City> worldCityFlux = this.worldCityRepository
                .deleteAll()
                .thenMany(this.worldCityRepository.save(new City(null, "Remscheid", "Germany", "NRW", "4711L")))
                .thenMany(this.worldCityRepository.findAll());
        StepVerifier
                .create(worldCityFlux)
                .expectNextMatches(worldCity -> worldCity.getId() != null &&
                        worldCity.getName().equalsIgnoreCase("remscheid"))
                .verifyComplete();
    }

    /**
     * Uncomment to execute this method just once.
     *
     * @throws IOException
     */
    @Test
    @Disabled
    void createAllCities() throws IOException {
        Resource resource = new ClassPathResource("/world-cities_csv.csv");
        List<String> strings = FileUtils.readLines(resource.getFile(), StandardCharsets.UTF_8);
        List<City> cities = strings
                .stream()
                .skip(1) // headers
                //.limit(5) // first 5 lines only
                .map(this::splitCityTuple)
                .map(this::createCity)
                .collect(toList());

        Long count = this.worldCityRepository
                .deleteAll()
                .thenMany(this.worldCityRepository.saveAll(cities))
                .then(this.worldCityRepository.count())
                .block();
        log.info("Count: {}", count);
    }

    private City createCity(String[] cityTuple) {
        return new City(null, cityTuple[0], cityTuple[1], cityTuple[2], cityTuple[3]);
    }

    private String[] splitCityTuple(String s) {
        return s.split(",");
    }
}