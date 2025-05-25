package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation.AddMedicalConsultationUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation.CancelMedicalConsultationUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation.FieldConsultationValidation;
import ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation.StatusMedicalConsultationUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;
import ucatolica.edu.co.audichek.utils.FieldValidation;

@Configuration
public class MedicalConsultationUseCaseConfig {
    @Bean
    public AddMedicalConsultationUseCase addMedicalConsultationUseCase(
            FieldConsultationValidation fieldValidation,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            MedicalConsultationRepository medicalConsultationRepository,
            StatusMedicalConsultationUseCase statusMedicalConsultationUseCase) {
        return new AddMedicalConsultationUseCase(
                fieldValidation,
                patientRepository,
                doctorRepository,
                medicalConsultationRepository,
                statusMedicalConsultationUseCase
        );
    }
    @Bean
    public CancelMedicalConsultationUseCase cancelMedicalConsultationUseCase(
            StatusMedicalConsultationUseCase statusMedicalConsultationUseCase,
            FieldValidation fieldValidationRolesPermissions,
            AppointmentRepository appointmentRepository,
            MedicalConsultationRepository medicalConsultationRepository,
            ConsultationStatusHistoryRepository consultationStatusHistoryRepository) {
        return new CancelMedicalConsultationUseCase(
                statusMedicalConsultationUseCase,
                fieldValidationRolesPermissions,
                appointmentRepository,
                medicalConsultationRepository,
                consultationStatusHistoryRepository
        );
    }
    @Bean
    public  FieldConsultationValidation fieldConsultationValidation(){
        return new FieldConsultationValidation();
    }
    @Bean
    public StatusMedicalConsultationUseCase statusMedicalConsultationUseCase(
            ConsultationStatusHistoryRepository consultationStatusHistoryRepository,
            ConsultationStatusRepository consultationStatusRepository) {
        return new StatusMedicalConsultationUseCase(
                consultationStatusHistoryRepository,
                consultationStatusRepository
        );
    }
}
