package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer.AppointmentSubject;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorAvailabilityRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CancelAppointmentUseCases {

    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final AppointmentValidationUseCases appointmentValidationUseCases;
    private final AppointmentSubject appointmentSubject;

    public CancelAppointmentUseCases(
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            AppointmentStatusUseCases appointmentStatusUseCases,
            AppointmentValidationUseCases appointmentValidationUseCases,
            AppointmentSubject appointmentSubject) {
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.appointmentValidationUseCases = appointmentValidationUseCases;
        this.appointmentSubject = appointmentSubject;
    }

    @Transactional
    public String appointmentCancel(String id) {
        // Find the appointment by ID or throw exception if it doesn't exist
        appointmentValidationUseCases.validateUUID(id);
        Appointment appointment = appointmentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Appointment with the given ID does not exist"));

        Optional <DoctorAvailability> availability =
                appointmentValidationUseCases.doctorAvailabilityValidation(appointment,false);


        availability.get().setIsAvailable(true);
        doctorAvailabilityRepository.save(availability.get());

        //change status
        List<AppointmentStatusHistory> appointmentStatusHistoryList = appointmentStatusUseCases.getAppointmentHistoryByAppointmentActivate(appointment) ;

        appointmentStatusHistoryList.getFirst().setIsActive(false);
        appointmentStatusUseCases.saveAppointmentStatusHistory(appointmentStatusHistoryList.getFirst());
        appointmentStatusUseCases.createAppointmentStatusHistory(appointment,AppointmentStatuses.CANCELLED.toString());


        // 🔔 Notificar a los observadores
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        AppointmentLogDTO logDTO = new AppointmentLogDTO();

        logDTO.setAction(AppointmentStatuses.CANCELLED.toString());
        logDTO.setUserId(userId); // asegúrate de que esté disponible
        logDTO.setPreviousAppointment(appointment);
        logDTO.setNewAppointment(null);
        appointmentSubject.notifyObservers(logDTO);

        return "Appointment deleted successfully";
    }
}
