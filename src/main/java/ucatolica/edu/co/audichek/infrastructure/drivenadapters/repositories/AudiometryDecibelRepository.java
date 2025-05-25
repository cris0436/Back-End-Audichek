package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryDecibel;

import java.util.Optional;
import java.util.UUID;

public interface AudiometryDecibelRepository extends JpaRepository<AudiometryDecibel, UUID> {
    Optional<AudiometryDecibel> findByDecibelLevel(Integer decibelLevel);
}