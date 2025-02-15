package nl.craftsmen.brewery.company;

import nl.craftsmen.brewery.model.Company;
import nl.craftsmen.brewery.model.Developer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    private final DeveloperRepository developerRepository;

    public CompanyService(CompanyRepository companyRepository, DeveloperRepository developerRepository) {
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
    }

    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Company> findCompanyByName(String name) {
        return companyRepository.findCompanyByNameIgnoreCase(name);
    }

    @Transactional
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findCompanyById(Integer companyId) {
        return companyRepository.findById(companyId);
    }

    @Transactional
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }

    @Transactional
    public Company updateCompany(Company currentCompany, Company newCompany) {
        currentCompany.setAddress(newCompany.getAddress());
        currentCompany.setCity(newCompany.getCity());
        currentCompany.setName(newCompany.getName());
        currentCompany.setTelephone(newCompany.getTelephone());
        return companyRepository.save(currentCompany);
    }

    public Optional<Developer> findDeveloperByName(String lastName, String firstName) {
        LOGGER.debug("Find developer by name '{} {}'", firstName, lastName);
        return developerRepository.findByLastNameIgnoreCaseAndFirstNameIgnoreCase(lastName, firstName);
    }
}
