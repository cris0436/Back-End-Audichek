package ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalConsultationRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CancelMedicalConsultationUseCase {
    private final StatusMedicalConsultationUseCase statusMedicalConsultationUseCase;
    private final FieldValidation fieldValidationRolesPermissions;
    private final AppointmentRepository appointmentRepository;
    private final MedicalConsultationRepository medicalConsultationRepository;
    private final ConsultationStatusHistoryRepository consultationStatusHistoryRepository;

    public CancelMedicalConsultationUseCase(StatusMedicalConsultationUseCase statusMedicalConsultationUseCase,
                                            FieldValidation fieldValidationRolesPermissions,
                                            AppointmentRepository appointmentRepository,
                                            MedicalConsultationRepository medicalConsultationRepository,
                                            ConsultationStatusHistoryRepository consultationStatusHistoryRepository) {
        this.statusMedicalConsultationUseCase = statusMedicalConsultationUseCase;
        this.fieldValidationRolesPermissions = fieldValidationRolesPermissions;
        this.appointmentRepository = appointmentRepository;
        this.medicalConsultationRepository = medicalConsultationRepository;
        this.consultationStatusHistoryRepository = consultationStatusHistoryRepository;
    }

    public String cancelMedicalConsultationByAppointment(String appointmentId){
        fieldValidationRolesPermissions.validateUUID(appointmentId);
        Optional<Appointment> appointment = appointmentRepository.findAppointmentById(UUID.fromString(appointmentId));
        if (appointment.isEmpty()){
            throw new BadRequestException("Appointment does not exist");
        }
        LocalDateTime medicalConsultationDate = LocalDateTime.of(appointment.get().getDateAppointment(), appointment.get().getEndTime());
        List<MedicalConsultation> medicalConsultation =
                medicalConsultationRepository.findMedicalConsultationByConsultationDateAndDoctorAndPatients(
                        medicalConsultationDate,
                        appointment.get().getDoctor(),
                        appointment.get().getPatient()
                );
        if (medicalConsultation.size() != 1){
            throw new ConflictException("You there is a problem call an admin");
        }
        List<ConsultationStatusHistory> consultationStatuses = consultationStatusHistoryRepository.findByMedicalConsultationAndIsActiveAndEstadosConsultaIdestadosConsulta(
                medicalConsultation.getFirst(),
                true,
                statusMedicalConsultationUseCase.addStatusIfNotExist("ACTIVATE")
        );
        if (consultationStatuses.size() != 1){
            throw new BadRequestException("Medical Consultation does not exist");
        }
        consultationStatuses.getFirst().setIsActive(false);
        consultationStatusHistoryRepository.save(consultationStatuses.getFirst());
        consultationStatusHistoryRepository.save(
                new ConsultationStatusHistory(medicalConsultation.getFirst(),statusMedicalConsultationUseCase.addStatusIfNotExist("CANCELLED"))
        );
        return "Medical Consultation canceled successfully";
    }
}
