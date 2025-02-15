package nl.craftsmen.brewery.project;

import java.util.Optional;
import nl.craftsmen.brewery.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findProjectByName(String name);
}
