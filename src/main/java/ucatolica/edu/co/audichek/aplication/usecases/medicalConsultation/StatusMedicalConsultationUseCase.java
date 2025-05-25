package ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationStatusRepository;

import java.util.List;


public class StatusMedicalConsultationUseCase {
    private final ConsultationStatusHistoryRepository consultationStatusHistoryRepository;
    private final ConsultationStatusRepository consultationStatusRepository;

    public StatusMedicalConsultationUseCase(ConsultationStatusHistoryRepository consultationStatusHistoryRepository,
                                            ConsultationStatusRepository consultationStatusRepository) {
        this.consultationStatusHistoryRepository = consultationStatusHistoryRepository;
        this.consultationStatusRepository = consultationStatusRepository;
    }

    public ConsultationStatusHistory createMedicalConsultationStatusHistory(MedicalConsultation medicalConsultation){
        return consultationStatusHistoryRepository.save(
          new ConsultationStatusHistory(
                  medicalConsultation
                  ,addStatusIfNotExist("ACTIVE")
          )
        );
    }

    public ConsultationStatus addStatusIfNotExist(String status){
        List<ConsultationStatus> consultationStatus= consultationStatusRepository.findByDescription(status);
        if (consultationStatus.size() != 1){
            throw new BadRequestException("Status does not exist");
        }
        return consultationStatus.getFirst();

    }

}
