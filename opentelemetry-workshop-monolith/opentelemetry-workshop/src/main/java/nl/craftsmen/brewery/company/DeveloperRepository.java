package nl.craftsmen.brewery.company;

import java.util.Optional;
import nl.craftsmen.brewery.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

    Optional<Developer> findByLastNameIgnoreCaseAndFirstNameIgnoreCase(String lastName, String firstName);
}
