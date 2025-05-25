package ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer;

import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;

public interface AppointmentObserver{
    void update(AppointmentLogDTO appointmentLogDTO);
}