package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.LegalRepresentative;

public interface LegalRepresentativeRepository extends JpaRepository<LegalRepresentative, Integer> {
}
