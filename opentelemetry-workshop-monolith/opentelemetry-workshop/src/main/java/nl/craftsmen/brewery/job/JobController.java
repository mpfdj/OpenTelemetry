package nl.craftsmen.brewery.job;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.craftsmen.brewery.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/jobs", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Job>> listJobs() {
        LOGGER.debug("List jobs");
        List<Job> jobs = jobService.findAll();
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(jobs);
        }
    }

    @GetMapping(value = "/job/{id}", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Job> getJob(@PathVariable("id") Integer id) {
        LOGGER.debug("Find job by id: {}", id);
        Optional<Job> job = jobService.findById(id);
        return job
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/job/new", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<Job> newJob(@RequestBody Job job) {
        String projectName = Objects.requireNonNull(job.getProject()).getName();
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
        LOGGER.error("Error creating job", jce);
        return ResponseEntity.badRequest().body(jce.getMessage());
    }
}
