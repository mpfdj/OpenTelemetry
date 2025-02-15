package nl.craftsmen.brewery.job;

import nl.craftsmen.brewery.model.Developer;
import nl.craftsmen.brewery.model.Project;

public class JobCreationException extends RuntimeException {

    public JobCreationException(Project project, Developer developer) {
        super(String.format("Error creating job with project %s and developer %s %s", project.getName(), developer.getFirstName(), developer.getLastName()));
    }
}
