package nl.craftsmen.brewery.job.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    @Value("${project.url}")
    private String projectUrl;

    private final RestTemplate restTemplate;

    public ProjectService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Optional<Project> findProjectByName(String name) {
        LOGGER.debug("Calling project service for {} at {}", name, projectUrl);
        Project project = restTemplate.getForObject(projectUrl + "/project/" + name, Project.class);
        return Optional.ofNullable(project);
    }
}
