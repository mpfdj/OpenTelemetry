package nl.craftsmen.brewery.project.controller;

import nl.craftsmen.brewery.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findProjectByNameIgnoreCase(String name);
}
