package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentLogDTO {
    public AppointmentLogDTO(){
        timestamp = LocalDateTime.now();
    }
    String action;
    String userId;
    LocalDateTime timestamp;
    Appointment previousAppointment ;
    Appointment newAppointment;
}
