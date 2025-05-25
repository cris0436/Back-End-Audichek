package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryData;

import java.util.List;
import java.util.UUID;

public interface AudiometryDataRepository extends JpaRepository<AudiometryData, UUID> {
    List<AudiometryData> findByAudiometryId(UUID audiometryId);
}
