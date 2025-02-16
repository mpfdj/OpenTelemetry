package nl.craftsmen.brewery.job.controller;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import nl.craftsmen.brewery.job.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    private final Tracer tracer;

    public JobController(OpenTelemetry openTelemetry, JobService jobService) {
        this.tracer = openTelemetry.getTracer(getClass().getName(), "0.1.0");
        this.jobService = jobService;
    }

    @GetMapping(value = "/jobs", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Job>> listJobs() {
        LOGGER.debug("List jobs");

        // Exercise 5 - Create your own span
        // TODO: add your own span
        Span span = tracer.spanBuilder("list jobs").startSpan();

        try (Scope scope = span.makeCurrent()) {
            List<Job> jobs = jobService.findAll();
            if (jobs.isEmpty()) {
                span.addEvent("Jobs not found");  // Exercise 9 - Add events
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                span.addEvent("Jobs found");  // Exercise 9 - Add events
                return ResponseEntity.ok(jobs);
            }
        } catch (Throwable t) {
            span.recordException(t);
        } finally {
            span.addEvent("JobController span ended"); // Exercise 9 - Add events
            span.end();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/job/{id}", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Job> getJob(@PathVariable("id") Integer id) {
        Optional<Job> job = jobService.findById(id);
        return job
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/job/new", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<Job> newJob(@RequestBody Job job) {
        String projectName = Objects.requireNonNull(job.getProjectName());
        LOGGER.debug("Creating new job for {}", projectName);
        Optional<Job> storedJob = jobService.findByProjectName(projectName);
        return storedJob
                .map(j -> {
                    LOGGER.warn("Job with project name {} already exists", projectName);
                    return new ResponseEntity<Job>(HttpStatus.BAD_REQUEST);
                })
                .orElse(new ResponseEntity<>(jobService.newJob(job), HttpStatus.CREATED));
    }

    @ExceptionHandler(JobCreationException.class)
    public ResponseEntity<String> exception(JobCreationException jce) {
        LOGGER.error(jce.getMessage());
        return ResponseEntity.badRequest().body(jce.getMessage());
    }
}
