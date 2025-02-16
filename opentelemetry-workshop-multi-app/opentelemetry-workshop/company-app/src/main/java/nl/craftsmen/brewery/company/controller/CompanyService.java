package nl.craftsmen.brewery.company.controller;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nl.craftsmen.brewery.company.model.Company;
import nl.craftsmen.brewery.company.model.Developer;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CompanyService {

    private static final Logger LOGGER = Logger.getLogger(CompanyService.class);

    @Inject
    CompanyRepository companyRepository;

    @Inject
    DeveloperRepository developerRepository;


    // Exercise 9 - Add events
    @Inject
    Span span;

    public List<Company> findAll() {
        LOGGER.debug("Find all companies");
        return companyRepository.listAll();
    }

    public Optional<Company> findCompanyByName(String name) {
        LOGGER.debug("Find company by name" + name);
        return companyRepository.findCompanyByName(name);
    }

    @Transactional
    public Object saveCompany(Company company) {
        LOGGER.debug("Persist company " + company.getName());
        companyRepository.persist(company);
        return company;
    }

    public Optional<Company> findCompanyById(Integer companyId) {
        LOGGER.debug("Find company by id " + companyId);
        return companyRepository.findByIdOptional(Long.valueOf(companyId));
    }

    @Transactional
    public void deleteCompany(Company company) {
        LOGGER.debug("Delete company " + company.getName());
        companyRepository.delete(company);
    }

    @Transactional
    public Company updateCompany(Integer companyId, Company newCompany) {
        LOGGER.debug("Update company " + newCompany.getName());
        Optional<Company> currentCompany  = companyRepository.findByIdOptional(Long.valueOf(companyId));
        return currentCompany.map(c -> {
            c.setAddress(newCompany.getAddress());
            c.setCity(newCompany.getCity());
            c.setName(newCompany.getName());
            c.setTelephone(newCompany.getTelephone());
            companyRepository.persist(c);
            return c;
        }).orElse(null);
    }

    public Optional<Developer> findDeveloperByName(String lastname, String firstname) {
        LOGGER.debug("find developer by name " + firstname + " " + lastname);
        // Exercise 9 - Add events
        span.addEvent("developer name", Attributes.of(AttributeKey.stringKey("name"), firstname + " " + lastname));
        return developerRepository.findDeveloperByName(lastname, firstname);
    }
}
