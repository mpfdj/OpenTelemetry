package nl.craftsmen.brewery.job.controller;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import nl.craftsmen.brewery.job.company.CompanyService;
import nl.craftsmen.brewery.job.company.Developer;
import nl.craftsmen.brewery.job.model.Job;
import nl.craftsmen.brewery.job.project.Project;
import nl.craftsmen.brewery.job.project.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private final JobRepository jobRepository;
    private final ProjectService projectService;
    private final CompanyService companyService;

    private final Tracer tracer;

    public JobService(JobRepository jobRepository, ProjectService projectService, CompanyService companyService, Tracer tracer) {
        this.jobRepository = jobRepository;
        this.projectService = projectService;
        this.companyService = companyService;
        this.tracer = tracer;
    }

    public List<Job> findAll() {
        // Exercise 6 - create a child span
        Span span = tracer.spanBuilder("find all jobs").startSpan();
        try (Scope scope = span.makeCurrent()) {
            return jobRepository.findAll().stream().peek(this::updateJobWithProjectAndDeveloper).collect(Collectors.toList());
        } finally {
            span.end();
        }
    }

    public Optional<Job> findById(Integer id) {
        return jobRepository.findById(id).map(this::updateJobWithProjectAndDeveloper);
    }

    public Optional<Job> findByProjectName(String name) {
        return jobRepository.findByProjectNameIgnoreCase(name).map(this::updateJobWithProjectAndDeveloper);
    }

    public Job newJob(Job job) {
        LOGGER.debug("Calling job service for project {}", job.getProjectName());
        Project project = projectService.findProjectByName(job.getProjectName()).orElse(null);
        Developer developer = companyService.findDeveloperByName(
                Objects.requireNonNull(job.getDeveloperLastName()),
                Objects.requireNonNull(job.getDeveloperFirstName())).orElse(null);

        if (project != null && developer != null) {
            if (project.getSkills().stream().distinct().anyMatch(s -> developer.getSkills().contains(s))) {
                Job newJob = new Job();
                newJob.setStartDate(job.getStartDate());
                newJob.setDescription(job.getDescription());
                newJob.setProjectName(project.getName());
                newJob.setDeveloperFirstName(developer.getFirstName());
                newJob.setDeveloperLastName(developer.getLastName());
                newJob.setDeveloper(developer);
                newJob.setProject(project);
                return jobRepository.save(newJob);
            } else {
                throw new JobCreationException(job.getProjectName(), job.getDeveloperLastName(), job.getDeveloperFirstName(),
                        "Skills do not match");
            }
        }

        throw new JobCreationException(job.getProjectName(), job.getDeveloperLastName(), job.getDeveloperFirstName(),
                "project and/or developer not found");
    }

    private Job updateJobWithProjectAndDeveloper(Job job) {
        job.setProject(projectService.findProjectByName(job.getProjectName()).orElse(null));
        job.setDeveloper(companyService.findDeveloperByName(job.getDeveloperLastName(), job.getDeveloperFirstName()).orElse(null));
        return job;
    }
}
