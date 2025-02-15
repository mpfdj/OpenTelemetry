package nl.craftsmen.brewery.job.controller;

public class JobCreationException extends RuntimeException {

    public JobCreationException(String projectName, String firstName, String lastName, String message) {
        super(String.format("Error creating job with project '%s' and developer '%s %s': %s", projectName, firstName, lastName, message));
    }
}
