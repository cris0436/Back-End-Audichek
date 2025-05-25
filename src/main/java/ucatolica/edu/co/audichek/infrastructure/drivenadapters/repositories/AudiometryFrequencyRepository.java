package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryFrequency;

import java.util.Optional;
import java.util.UUID;

public interface AudiometryFrequencyRepository extends JpaRepository<AudiometryFrequency, UUID> {
    Optional<AudiometryFrequency> findByFrequencyHz(Integer frequencyHz);
}
