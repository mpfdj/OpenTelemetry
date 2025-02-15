package nl.craftsmen.brewery.job.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Value("${company.url}")
    private String companyUrl;

    private final RestTemplate restTemplate;

    public CompanyService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<Developer> findDeveloperByName(String lastName, String firstName) {
        LOGGER.debug("Calling company service for developer {} {} at {}", firstName, lastName, companyUrl);

        Developer developer = restTemplate
                .getForObject(companyUrl + "/developer/" + lastName + "/" + firstName, Developer.class);
        return Optional.ofNullable(developer);
    }
}
