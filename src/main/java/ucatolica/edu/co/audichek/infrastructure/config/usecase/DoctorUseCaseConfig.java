package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.GetDoctorSpeciality;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.AppointmentStatusUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.AddDoctorUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.ConfirmedAppointmentUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.GetDoctorUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.ValidateDoctor;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonValidationUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorResponseDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

@Configuration
public class DoctorUseCaseConfig {
    @Bean
    public AddDoctorUseCase addDoctorUseCase(DoctorRepository doctorRepository,
                                             FieldValidation<Doctor> validateDoctor,
                                             PersonValidationUseCases validatePerson,
                                             MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO,
                                             MapperToEntity<Doctor, DoctorRequestDTO> mapperToEntity,
                                             PersonRepository personRepository,
                                             PersonStatusHistoryRepository personStatusHistoryRepository,
                                             ManageRolesPermissionsUseCases manageRolesPermissionsUseCases,
                                             GetDoctorSpeciality getDoctorSpeciality) {

        return new AddDoctorUseCase(
                doctorRepository,
                validateDoctor,
                validatePerson,
                mapperToDTO,
                mapperToEntity,
                personRepository,
                personStatusHistoryRepository,
                manageRolesPermissionsUseCases,
                getDoctorSpeciality
        );
    }

    @Bean
    public ConfirmedAppointmentUseCase confirmedAppointmentUseCase(
            PersonRepository personRepository,
            AppointmentStatusHistoryRepository appointmentStatusHistoryRepository,
            PersonValidationUseCases personValidation,
            AppointmentRepository appointmentRepository,
            AppointmentStatusUseCases appointmentStatusUseCases,
            MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO) {

        return new ConfirmedAppointmentUseCase(
                personRepository,
                appointmentStatusHistoryRepository,
                personValidation,
                appointmentRepository,
                appointmentStatusUseCases,
                mapperToDTO
        );
    }
    @Bean
    public GetDoctorUseCase getDoctorUseCase(DoctorRepository doctorRepository,
                                             PersonValidationUseCases personValidation,
                                             MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO) {

        return new GetDoctorUseCase(doctorRepository, personValidation, mapperToDTO);
    }

    @Bean
    public ValidateDoctor validateDoctor() {
        return new ValidateDoctor();
    }


}
