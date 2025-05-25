package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "appointment_audit_log")
public class AppointmentAuditLog {
    public AppointmentAuditLog() {}
    public AppointmentAuditLog(String action, String userId, Appointment previousAppointment, Appointment newAppointment) {
        this.action = action;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
        this.previousAppointment = previousAppointment;
        this.newAppointment = newAppointment;
    }
    @Id
    @GeneratedValue
    @Column(name = "audit_id", nullable = false)
    private UUID id;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "previous_appointment_id")
    private Appointment previousAppointment;

    @ManyToOne
    @JoinColumn(name = "new_appointment_id")
    private Appointment newAppointment;
}