package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Audiometry;

import java.util.UUID;

public interface AudiometryRepository extends JpaRepository<Audiometry, UUID> {
}
