package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentAuditLog {
    public AppointmentAuditLog() {}
    public AppointmentAuditLog(String action, String userId, Appointment previousAppointment, Appointment newAppointment) {
        this.action = action;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
        this.previousAppointment = previousAppointment;
        this.newAppointment = newAppointment;
    }
    private UUID id;

    private String action;

    private String userId;

    private LocalDateTime timestamp;

    private Appointment previousAppointment;

    private Appointment newAppointment;
}