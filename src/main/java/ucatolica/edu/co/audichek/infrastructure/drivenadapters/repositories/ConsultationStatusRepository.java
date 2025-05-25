package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultationStatusRepository extends JpaRepository<ConsultationStatus, UUID> {
    List<ConsultationStatus> findByDescription(String description);
}
