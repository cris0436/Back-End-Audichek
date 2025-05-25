package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "appointment_status_history")
public class AppointmentStatusHistory {
    public AppointmentStatusHistory() {}

    public AppointmentStatusHistory(AppointmentStatus appointmentStatus, Appointment appointment) {
        this.appointmentStatus = appointmentStatus;
        this.appointment = appointment;
        this.changeDate = LocalDateTime.now();
        this.isActive = true;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_status_history_id", nullable = false )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false ,referencedColumnName = "appointment_id" )
    private Appointment appointment;  // Relación unidireccional a `Appointment`

    @ManyToOne
    @JoinColumn(name = "appointment_status_id", nullable = false ,referencedColumnName = "appointment_status_id")
    private AppointmentStatus appointmentStatus;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate ;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive ;

    @Override
    public String toString() {
        return "AppointmentStatusHistory{" +
                "id=" + id +
                ", appointment=" + (appointment != null ? appointment.getId() : "null") +
                ", appointmentStatus=" + (appointmentStatus != null ? appointmentStatus.getDescripcion() : "null") +
                ", changeDate=" + changeDate +
                ", isActive=" + isActive +
                '}';
    }


}
