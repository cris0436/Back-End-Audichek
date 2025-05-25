package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MedicalConsultationRepository extends JpaRepository<MedicalConsultation, UUID> {
    List<MedicalConsultation> findMedicalConsultationByConsultationDateAndDoctorAndPatients(LocalDateTime consultationDate, Doctor doctor, Patient patients);
}
