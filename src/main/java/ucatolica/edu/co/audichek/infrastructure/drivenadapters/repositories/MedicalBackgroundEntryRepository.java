package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalBackgroundEntry;

import java.util.List;
import java.util.UUID;

public interface MedicalBackgroundEntryRepository extends JpaRepository<MedicalBackgroundEntry, UUID> {
    List<MedicalBackgroundEntry> findByPatientId(UUID patientId);
}

