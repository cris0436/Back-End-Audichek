package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryType;

import java.util.UUID;

public interface AudiometryTypeRepository extends JpaRepository<AudiometryType, UUID> {
}
