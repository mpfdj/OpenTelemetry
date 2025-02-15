package nl.craftsmen.brewery.company;

import java.util.Optional;
import nl.craftsmen.brewery.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findCompanyByNameIgnoreCase(String name);
}
