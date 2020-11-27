package de.ksbrwsk.citiies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;

@RestController
@Log4j2
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/api/cities")
    public Flux<City> getAll() {
        return this.cityRepository
                .findAll()
                .limitRequest(10);
    }

    @GetMapping("/api/cities/byName/{name}")
    public Flux<City> getAllByName(@PathVariable String name) {
        return this.cityRepository
                .findAllByName(name);
    }

    @GetMapping("/api/cities/byCountry/{country}")
    public Flux<City> getAllByCountry(@NotNull @PathVariable String country) {
        return this.cityRepository
                .findAllByCountry(country);
    }

    @GetMapping("/api/cities/bySubcountry/{subcountry}")
    public Flux<City> getAllBySubcountry(@PathVariable String subcountry) {
        return this.cityRepository
                .findAllBySubcountry(subcountry);
    }
}
