package de.ksbrwsk.citiies;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CityRepository extends ReactiveCrudRepository<City, Long> {

    Flux<City> findAllByName(String name);

    Flux<City> findAllByCountry(String country);

    Flux<City> findAllBySubcountry(String subcountry);
}
