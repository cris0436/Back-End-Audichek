package ucatolica.edu.co.audichek.aplication.usecases.doctor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.AppointmentStatusUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonValidationUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ConfirmedAppointmentUseCase {
    private final PersonRepository personRepository;
    private final AppointmentStatusHistoryRepository appointmentStatusHistoryRepository;
    private final PersonValidationUseCases personValidation;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO;

    public ConfirmedAppointmentUseCase(PersonRepository personRepository,
                                       AppointmentStatusHistoryRepository appointmentStatusHistoryRepository,
                                       PersonValidationUseCases personValidation,
                                       AppointmentRepository appointmentRepository,
                                       AppointmentStatusUseCases appointmentStatusUseCases,
                                       MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO) {
        this.personRepository = personRepository;
        this.appointmentStatusHistoryRepository = appointmentStatusHistoryRepository;
        this.personValidation = personValidation;
        this.appointmentRepository = appointmentRepository;
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.mapperToDTO = mapperToDTO;
    }



    public List<AppointmentResponseDTO> getMyPendingAppointments(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<Person> personAuth = personRepository.findPersonById(userId);
        if (personAuth.get().getDoctor() == null) {
            throw new BadRequestException("The doctor does not exist");
        }
        List<Appointment> appointments = Optional.ofNullable(
                        appointmentStatusHistoryRepository.findByAppointment_DoctorAndIsActiveAndAppointmentStatus_Descripcion(
                                personAuth.get().getDoctor(), true, "PENDING")
                ).orElse(Collections.emptyList())
                .stream()
                .map(AppointmentStatusHistory::getAppointment)
                .toList();
        return appointments.stream().map(mapperToDTO::toDTO).toList();
    }

    public String confirmAppointment(String appointmentId) {
        personValidation.verifyID(appointmentId);
        Appointment appointment = appointmentRepository.findAppointmentById(UUID.fromString(appointmentId))
                .orElseThrow(() -> new BadRequestException("Appointment not found"));

        List<AppointmentStatusHistory> appointmentStatusHistory = Optional.ofNullable(
                appointmentStatusHistoryRepository.findAppointmentStatusHistoriesByAppointment(appointment)
        ).orElseThrow(() -> new BadRequestException("Appointment not found"));

        appointmentStatusHistory.getFirst().setIsActive(false);
        appointmentRepository.save(appointment);
        appointmentStatusUseCases.createAppointmentStatusHistory(
                        appointment ,"ACTIVATE"
                );

        return "Appointment confirmed successfully";

    }
}
