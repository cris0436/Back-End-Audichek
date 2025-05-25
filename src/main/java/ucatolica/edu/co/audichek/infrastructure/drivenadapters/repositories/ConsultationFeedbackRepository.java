package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationFeedback;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationFeedbackRepository extends JpaRepository<ConsultationFeedback, UUID> {
    Optional<ConsultationFeedback> findByMedicalConsultation(MedicalConsultation consultation);
}
