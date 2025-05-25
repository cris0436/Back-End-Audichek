package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer.AppointmentSubject;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AppointmentMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.DoctorAvailabilityUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;

import java.util.Optional;
import java.util.UUID;

@Service
public class RescheduleAppointmentUseCases {
    private final AppointmentValidationUseCases appointmentValidationUseCases;
    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityUseCases doctorAvailabilityUseCases;
    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final MapperToEntity<Appointment, AppointmentRequestDTO> mapperToEntity;
    private final AppointmentMapper appointmentMapper;
    private final AddAppointmentUseCases addAppointmentUseCases;
    private final AppointmentSubject appointmentSubject;

    public RescheduleAppointmentUseCases(
            AppointmentValidationUseCases appointmentValidationUseCases,
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityUseCases doctorAvailabilityUseCases,
            AppointmentStatusUseCases appointmentStatusUseCases,
            MapperToEntity<Appointment, AppointmentRequestDTO> mapperToEntity,
            AppointmentMapper appointmentMapper,
            AddAppointmentUseCases addAppointmentUseCases,
            AppointmentSubject appointmentSubject) {

        this.appointmentValidationUseCases = appointmentValidationUseCases;
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityUseCases = doctorAvailabilityUseCases;
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.mapperToEntity = mapperToEntity;
        this.appointmentMapper = appointmentMapper;
        this.addAppointmentUseCases = addAppointmentUseCases;
        this.appointmentSubject = appointmentSubject;
    }


    @Transactional
    public AppointmentResponseDTO rescheduleAppointment(String appointmentIdString, AppointmentRequestDTO updatedAppointmentDTO) {
        // Validate the input data for the updated appointment (date, time, doctor, etc.)

        appointmentValidationUseCases.validateUUID(appointmentIdString);
        appointmentValidationUseCases.validateField(updatedAppointmentDTO);
        UUID appointmentId = UUID.fromString(appointmentIdString);

        Appointment updatedAppointment = mapperToEntity.toEntity(updatedAppointmentDTO);
        appointmentValidationUseCases.existingValidate(updatedAppointment);

        // Retrieve the existing appointment by its ID or throw a custom exception if not found
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Requested appointment does not exist"));

        // Find the previous availability slot that was used by the appointment (marked as unavailable)
        Optional<DoctorAvailability> previousAvailability =
                appointmentValidationUseCases.doctorAvailabilityValidation(existingAppointment,false);

        // Mark the previous availability as available again
        doctorAvailabilityUseCases.updateAvailability(previousAvailability.get().getId(), true);

        appointmentStatusUseCases.createAppointmentStatusHistory(existingAppointment,AppointmentStatuses.REPROGRAMMED.toString());
        AppointmentResponseDTO newAppointmentDTO = addAppointmentUseCases.addAppointment(updatedAppointmentDTO);
        Appointment newAppointment = appointmentRepository.findById(newAppointmentDTO.getId()).orElseThrow(()->new NotFoundException("Appointment not found"));

        // Notificar a los observadores
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        AppointmentLogDTO logDTO = new AppointmentLogDTO();
        logDTO.setAction(AppointmentStatuses.REPROGRAMMED.toString());
        logDTO.setUserId(userId);
        logDTO.setPreviousAppointment(existingAppointment);
        logDTO.setNewAppointment(newAppointment);
        appointmentSubject.notifyObservers(logDTO);

        return appointmentMapper.toDTO(newAppointment);
    }
}
