package de.ksbrwsk.citiies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Log4j2
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CityControllerStreamTest {

    @Autowired
    CityStreamController cityController;

    @Test
    void stream() {
        WebTestClient webTestClient = WebTestClient
                .bindToController(cityController).build();

        City city = webTestClient
                .get()
                .uri("/api/cities/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(City.class)
                .getResponseBody()
                .limitRequest(10)
                .blockLast();
        assertNotNull(city);
        log.info("Last City: {}", city);
    }
}