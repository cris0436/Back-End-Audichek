package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentStatusHistory {
    public AppointmentStatusHistory() {}

    public AppointmentStatusHistory(AppointmentStatus appointmentStatus, Appointment appointment) {
        this.appointmentStatus = appointmentStatus;
        this.appointment = appointment;
        this.changeDate = LocalDateTime.now();
        this.isActive = true;
    }


    private Integer id;

    private Appointment appointment;  // Relación unidireccional a `Appointment`

    private AppointmentStatus appointmentStatus;

    private LocalDateTime changeDate ;

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