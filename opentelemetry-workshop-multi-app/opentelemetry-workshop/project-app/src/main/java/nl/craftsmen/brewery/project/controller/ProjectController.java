package nl.craftsmen.brewery.project.controller;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import nl.craftsmen.brewery.project.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    private final Tracer tracer;

    public ProjectController(ProjectService projectService, OpenTelemetry openTelemetry) {
        this.projectService = projectService;
        this.tracer = openTelemetry.getTracer(getClass().getName(), "0.1.0");
    }

    @GetMapping(value = "/projects", produces = "application/json")
    public ResponseEntity<List<Project>> listProjects() {
        LOGGER.debug("List projects");
        List<Project> projects = projectService.findAll();
        if (!projects.isEmpty()) {
            return ResponseEntity.ok(projects);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Exercise 7 - Create a remote span
    @GetMapping(value = "/project/{name}", produces = "application/json")
    public ResponseEntity<Project> getProject(@PathVariable("name") String name) {
        LOGGER.debug("Get project by name '{}'", name);
        Span span = tracer.spanBuilder("find project by name").startSpan();
        try (Scope scope = span.makeCurrent()){
            Optional<Project> project = projectService.findProjectByName(name);
            return project
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } finally {
            span.end();
        }
    }

    @PostMapping(value = "project/new", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Project> newProject(@RequestBody Project project) {
        LOGGER.debug("New project with name '{}'", project.getName());
        Optional<Project> projectByName = projectService.findProjectByName(project.getName());
        return projectByName.map(p -> new ResponseEntity<Project>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(projectService.newProject(project), HttpStatus.CREATED));
    }
}
