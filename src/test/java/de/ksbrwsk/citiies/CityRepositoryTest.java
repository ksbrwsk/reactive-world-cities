package de.ksbrwsk.citiies;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Log4j2
class CityRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    CityRepository worldCityRepository;

    @Test
    @DisplayName("should load cities by name")
    void should_load_cities_by_name() {
        Flux<City> cityFlux = this.worldCityRepository
                .findAllByName("Remscheid")
                .limitRequest(1);
        StepVerifier
                .create(cityFlux)
                .expectNextMatches(city -> city.getName().equalsIgnoreCase("remscheid"))
                .verifyComplete();
    }

    @Test
    @DisplayName("should load cities by country")
    void should_load_cities_by_country() {
        Flux<City> cityFlux = this.worldCityRepository
                .findAllByCountry("Germany")
                .limitRequest(1);
        StepVerifier
                .create(cityFlux)
                .expectNextMatches(city -> city.getCountry().equalsIgnoreCase("germany"))
                .verifyComplete();
    }

    @Test
    @DisplayName("should load cities by subcountry")
    void should_load_cities_by_subcountry() {
        Flux<City> cityFlux = this.worldCityRepository
                .findAllBySubcountry("Bavaria")
                .limitRequest(1);
        StepVerifier
                .create(cityFlux)
                .expectNextMatches(city -> city.getSubcountry().equalsIgnoreCase("bavaria"))
                .verifyComplete();
    }

    @Test
    @DisplayName("should persist a new city")
    void should_persist_city() {
        Flux<City> worldCityFlux = this.worldCityRepository
                .deleteAll()
                .thenMany(this.worldCityRepository.save(new City(null, "NewCity", "Germany", "Bavaria", "4711L")))
                .thenMany(this.worldCityRepository.findAll());
        StepVerifier
                .create(worldCityFlux)
                .expectNextMatches(worldCity -> worldCity.getId() != null &&
                        worldCity.getName().equalsIgnoreCase("NewCity"))
                .verifyComplete();
    }
}