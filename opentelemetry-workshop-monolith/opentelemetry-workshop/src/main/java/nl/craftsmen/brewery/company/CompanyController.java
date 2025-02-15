package nl.craftsmen.brewery.company;

import nl.craftsmen.brewery.model.Company;
import nl.craftsmen.brewery.model.Developer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> listCompanies() {
        LOGGER.debug("List all companies");
        List<Company> companies = companyService.findAll();
        if (!companies.isEmpty()) {
            return ResponseEntity.ok(companies);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/company/{name}")
    public ResponseEntity<Company> getCompany(@PathVariable("name") String name) {
        LOGGER.debug("Get company by name '{}", name);
        Optional<Company> company = companyService.findCompanyByName(name);
        return company.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/company/new")
    public ResponseEntity<Company> newCompany(@RequestBody Company company) {
        LOGGER.debug("Create new company with name '{}", company.getName());
        Optional<Company> companyByName = companyService.findCompanyByName(company.getName());
        if (companyByName.isEmpty()) {
            return new ResponseEntity<>(companyService.saveCompany(company), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<Company> deleteCompany(@PathVariable("companyId") Integer companyId) {
        LOGGER.debug("Get company by id {}", companyId);
        Optional<Company> company = this.companyService.findCompanyById(companyId);
        return company.map(c -> {
            this.companyService.deleteCompany(c);
            return new ResponseEntity<Company>(HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/company/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable("companyId") Integer companyId, @RequestBody Company company) {
        LOGGER.debug("Update company with id {}", companyId);
        Optional<Company> currentCompany = this.companyService.findCompanyById(companyId);
        return currentCompany.map(c -> {
            Company updatedCompany = this.companyService.updateCompany(c, company);
            return new ResponseEntity<>(updatedCompany, HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/company/{companyId}/developer/{developerId}")
    public ResponseEntity<Developer> getCompanyDeveloper(@PathVariable("companyId") Integer companyId, @PathVariable("developerId") Integer developerId) {
        LOGGER.debug("Get developer with id {} from company with id {}", developerId, companyId);
        Optional<Company> company = companyService.findCompanyById(companyId);
        return company.map(c ->
                c.getDevelopers().stream().filter(developer -> developerId.equals(developer.getId()))
                        .findFirst().map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND))
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
