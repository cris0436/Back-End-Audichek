package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentStatusHistoryRepository;


import java.util.List;


public class ConfirmAppointmentUseCases {


    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentStatusHistoryRepository appointmentStatusHistoryRepository;

    public ConfirmAppointmentUseCases(AppointmentStatusUseCases appointmentStatusUseCases,
                                      AppointmentRepository appointmentRepository,
                                      AppointmentStatusHistoryRepository appointmentStatusHistoryRepository) {
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.appointmentRepository = appointmentRepository;
        this.appointmentStatusHistoryRepository = appointmentStatusHistoryRepository;
    }

    public String confirmAppointment(Integer id) {
        // validation
        List<AppointmentStatusHistory> appointmentStatusHistory = appointmentRepository.findPendingByAppointmentId(id ,AppointmentStatuses.PENDING.toString());
        if (appointmentStatusHistory.isEmpty()) {
            throw new BadRequestException("dont find pending active status");
        }
        appointmentStatusHistory.getFirst().setIsActive(false);
        appointmentStatusHistoryRepository.save(appointmentStatusHistory.getFirst());
        appointmentStatusUseCases.createAppointmentStatusHistory(appointmentStatusHistory.getFirst().getAppointment(),AppointmentStatuses.CONFIRMED.toString());

        return "appointment Confirmed successfully";
    }

}
