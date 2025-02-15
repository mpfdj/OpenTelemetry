package nl.craftsmen.brewery.project.controller;

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

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
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

    @GetMapping(value = "/project/{name}", produces = "application/json")
    public ResponseEntity<Project> getProject(@PathVariable String name) {
        LOGGER.debug("Get project by name '{}'", name);
        Optional<Project> project = projectService.findProjectByName(name);
        return project
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "project/new", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Project> newProject(@RequestBody Project project) {
        LOGGER.debug("New project with name '{}'", project.getName());
        Optional<Project> projectByName = projectService.findProjectByName(project.getName());
        return projectByName.map(p -> new ResponseEntity<Project>(HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<>(projectService.newProject(project), HttpStatus.CREATED));
    }
}
