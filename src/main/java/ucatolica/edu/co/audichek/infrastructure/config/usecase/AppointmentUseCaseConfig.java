package ucatolica.edu.co.audichek.infrastructure.config.usecase;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.GetAppointmentType;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.GetDoctorSpeciality;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.ManageSpecialitiesAppointmentTypes;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.*;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer.AuditLogObserver;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer.AppointmentObserver;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer.AppointmentSubject;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.DoctorAvailabilityUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.GetAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentAuditLog;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AppointmentMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;

@Configuration
public class AppointmentUseCaseConfig {
    @Bean
    public AppointmentStatusUseCases appointmentStatusUseCases(
            AppointmentStatusRepository appointmentStatusRepository,
            PatientRepository patientRepository,
            AppointmentStatusHistoryRepository appointmentStatusHistoryRepository,
            AppointmentRepository appointmentRepository) {

        return new AppointmentStatusUseCases(
                appointmentStatusRepository,
                patientRepository,
                appointmentStatusHistoryRepository,
                appointmentRepository
        );
    }
    @Bean
    public AddAppointmentUseCases addAppointmentUseCases(
            AppointmentStatusUseCases appointmentStatusUseCases,
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityUseCases doctorAvailabilityUseCases,
            PatientRepository patientRepository,
            AppointmentValidationUseCases appointmentValidationUseCases,
            AppointmentMapper appointmentMapper,
            DoctorRepository doctorRepository,
            GetAvailability getAvailability,
            GetAppointmentType getAppointmentType) {

        return new AddAppointmentUseCases(
                appointmentStatusUseCases,
                appointmentRepository,
                doctorAvailabilityUseCases,
                patientRepository,
                appointmentValidationUseCases,
                appointmentMapper,
                doctorRepository,
                getAvailability,
                getAppointmentType
        );
    }
    @Bean
    public AppointmentValidationUseCases appointmentValidationUseCases(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        return new AppointmentValidationUseCases(
                doctorAvailabilityRepository,
                patientRepository,
                doctorRepository
        );
    }
    @Bean
    public CancelAppointmentUseCases cancelAppointmentUseCases(
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            AppointmentStatusUseCases appointmentStatusUseCases,
            AppointmentValidationUseCases appointmentValidationUseCases,
            AppointmentSubject appointmentSubject) {

        return new CancelAppointmentUseCases(
                appointmentRepository,
                doctorAvailabilityRepository,
                appointmentStatusUseCases,
                appointmentValidationUseCases,
                appointmentSubject
        );
    }
    @Bean
    public ConfirmAppointmentUseCases confirmAppointmentUseCases(
            AppointmentStatusUseCases appointmentStatusUseCases,
            AppointmentRepository appointmentRepository,
            AppointmentStatusHistoryRepository appointmentStatusHistoryRepository) {

        return new ConfirmAppointmentUseCases(
                appointmentStatusUseCases,
                appointmentRepository,
                appointmentStatusHistoryRepository
        );
    }
    @Bean
    public GetAppointmentAudiLog getAppointmentAudiLog(
            AppointmentAuditLogRepository appointmentAuditLogRepository,
            MapperToDTO<AppointmentAuditLog, AppointmentLogResponseDTO> mapperToDTO) {

        return new GetAppointmentAudiLog(appointmentAuditLogRepository, mapperToDTO);
    }
    @Bean
    public GetAppointmentUseCases getAppointmentUseCases(
            AppointmentRepository appointmentRepository,
            MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO,
            AppointmentValidationUseCases appointmentValidationUseCases) {

        return new GetAppointmentUseCases(
                appointmentRepository,
                mapperToDTO,
                appointmentValidationUseCases
        );
    }
    @Bean
    public RescheduleAppointmentUseCases rescheduleAppointmentUseCases(
            AppointmentValidationUseCases appointmentValidationUseCases,
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityUseCases doctorAvailabilityUseCases,
            AppointmentStatusUseCases appointmentStatusUseCases,
            MapperToEntity<Appointment, AppointmentRequestDTO> mapperToEntity,
            AppointmentMapper appointmentMapper,
            AddAppointmentUseCases addAppointmentUseCases,
            AppointmentSubject appointmentSubject) {

        return new RescheduleAppointmentUseCases(
                appointmentValidationUseCases,
                appointmentRepository,
                doctorAvailabilityUseCases,
                appointmentStatusUseCases,
                mapperToEntity,
                appointmentMapper,
                addAppointmentUseCases,
                appointmentSubject
        );
    }
    @Bean
    public AuditLogObserver auditLogObserver(
            AppointmentAuditLogRepository auditRepository,
            MapperToEntity<AppointmentAuditLog, AppointmentLogDTO> mapperToEntity) {

        return new AuditLogObserver(auditRepository, mapperToEntity);
    }

    @Bean
    public AppointmentSubject appointmentSubject(List<AppointmentObserver> observers) {
        return new AppointmentSubject(observers);
    }
    @Bean
    public GetAppointmentType getAppointmentType(
            AppointmentTypeRepository appointmentTypeRepository,
            FieldValidation<String> fieldValidation,
            GetDoctorSpeciality getDoctorSpeciality,
            AppointmentTypesSpecialitiesRepository appointmentTypesSpecialitiesRepository) {

        return new GetAppointmentType(
                appointmentTypeRepository,
                fieldValidation,
                getDoctorSpeciality,
                appointmentTypesSpecialitiesRepository
        );
    }
    @Bean
    public GetDoctorSpeciality getDoctorSpeciality(
            DoctorSpecialityRepository doctorSpecialityRepository,
            FieldValidation<String> fieldValidation) {

        return new GetDoctorSpeciality(doctorSpecialityRepository, fieldValidation);
    }

    @Bean
    public ManageSpecialitiesAppointmentTypes manageSpecialitiesAppointmentTypes() {
        return new ManageSpecialitiesAppointmentTypes();
    }

    @Bean AppointmentReminderUseCase appointmentReminderUseCase(AppointmentRepository appointmentRepository) {
        return new AppointmentReminderUseCase(appointmentRepository);
    }






}