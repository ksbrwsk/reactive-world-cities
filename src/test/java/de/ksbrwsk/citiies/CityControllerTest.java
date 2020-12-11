package de.ksbrwsk.citiies;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Log4j2
class CityControllerTest extends AbstractIntegrationTest {

    @Autowired
    CityController cityController;

    @Test
    @DisplayName("should load all cities")
    void should_load_all_cities() {
        WebTestClient webTestClient = WebTestClient.bindToController(cityController).build();

        webTestClient
                .get()
                .uri("/api/cities")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("should load cities by name")
    void should_load_cities_by_name() {
        WebTestClient webTestClient = WebTestClient.bindToController(cityController).build();

        webTestClient
                .get()
                .uri("/api/cities/byName/Köln")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("@.[0].name")
                .isEqualTo("Köln");
    }

    @Test
    @DisplayName("should load cities by country")
    void should_load_cities_by_country() {
        WebTestClient webTestClient = WebTestClient.bindToController(cityController).build();

        webTestClient
                .get()
                .uri("/api/cities/byCountry/Germany")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("@.[0].country")
                .isEqualTo("Germany");
    }

    @Test@DisplayName("should load cities by subcountry")
    void should_load_cities_by_subcountry() {
        WebTestClient webTestClient = WebTestClient.bindToController(cityController).build();

        webTestClient
                .get()
                .uri("/api/cities/bySubcountry/North Rhine-Westphalia")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("@.[0].subcountry")
                .isEqualTo("North Rhine-Westphalia");
    }
}