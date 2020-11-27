package de.ksbrwsk.citiies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@Log4j2
@RequiredArgsConstructor
public class CityStreamController {

    private final CityRepository cityRepository;

    @GetMapping(value = "/api/cities/stream")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ServerSentEvent<City>> cityStream() {
        return this.cityRepository
                .findAll()
                .delayElements(Duration.ofMillis(500))
                .map(city -> ServerSentEvent.<City>builder()
                        .id(String.valueOf(city.getId()))
                        .event("city-event")
                        .data(city)
                        .build());
    }
}
