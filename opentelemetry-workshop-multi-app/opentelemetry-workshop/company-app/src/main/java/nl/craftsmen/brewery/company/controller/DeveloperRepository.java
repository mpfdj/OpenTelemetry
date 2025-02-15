package nl.craftsmen.brewery.company.controller;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.craftsmen.brewery.company.model.Developer;

import java.util.Optional;

@ApplicationScoped
public class DeveloperRepository implements PanacheRepository<Developer> {

    Optional<Developer> findDeveloperByName(String lastname, String firstname) {
        return find("firstName = ?1 and lastName = ?2", firstname, lastname).firstResultOptional();
    }

}
