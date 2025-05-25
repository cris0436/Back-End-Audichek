package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultationStatusHistoryRepository extends JpaRepository<ConsultationStatusHistory, UUID> {
    List<ConsultationStatusHistory> findByMedicalConsultationAndIsActiveAndEstadosConsultaIdestadosConsulta(MedicalConsultation medicalConsultation, Boolean isActive, ConsultationStatus estadosConsultaIdestadosConsulta);

    ConsultationStatusHistory findByMedicalConsultation(MedicalConsultation medicalConsultation);
}
