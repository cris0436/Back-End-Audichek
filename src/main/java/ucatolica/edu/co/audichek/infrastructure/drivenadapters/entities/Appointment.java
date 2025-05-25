package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "appointment_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "patient_id")
    private Patient patient;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;  // Start time of the availability.

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;  // End time of the availability.

    @Column(name = "date_appointment")
    private LocalDate dateAppointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "appointment_type_id", nullable = false)
    private AppointmentType appointmentType;

    @Column(name = "description", nullable = false, length = 400)
    private String description;


}
