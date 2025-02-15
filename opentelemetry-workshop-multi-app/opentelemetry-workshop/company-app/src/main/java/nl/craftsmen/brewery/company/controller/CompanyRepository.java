package nl.craftsmen.brewery.company.controller;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.craftsmen.brewery.company.model.Company;

import java.util.Optional;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {

    Optional<Company> findCompanyByName(String name) {
        return find("name", name).firstResultOptional();
    }

}
