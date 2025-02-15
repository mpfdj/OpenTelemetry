package nl.craftsmen.brewery.job.controller;

import nl.craftsmen.brewery.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Optional<Job> findByProjectNameIgnoreCase(String name);
}
