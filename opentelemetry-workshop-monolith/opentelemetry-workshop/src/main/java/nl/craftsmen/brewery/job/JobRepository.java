package nl.craftsmen.brewery.job;

import java.util.Optional;
import nl.craftsmen.brewery.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Optional<Job> findByProject_NameIgnoreCase(String name);
}
