package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "doctor_availabilities")
public class DoctorAvailability implements Serializable {
    public DoctorAvailability() {}
    public DoctorAvailability(LocalDate dateAvailability, LocalTime startTime, LocalTime endTime, Doctor doctor) {
        this.dateAvailability = dateAvailability;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;
        this.doctor = doctor;
    }
    @Id
    @GeneratedValue
    @Column(name = "availability_id", nullable = false, updatable = false)
    private UUID id;  // ID of the availability record.

    @Column(name = "date_availability")
    private LocalDate dateAvailability;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;  // Start time of the availability.

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;  // End time of the availability.

    @Column(name = "is_available", nullable = false ,columnDefinition = "boolean default true")
    private Boolean isAvailable;  // Boolean to indicate if the doctor is available during the time slot.

    // Adding the relationship to the Doctor entity
    @ManyToOne(cascade = CascadeType.REMOVE)  // Ensure that the doctor availability is removed when the doctor is deleted
    @JoinColumn(name = "doctor_id", nullable = false)  // Foreign key column to Doctor
    private Doctor doctor;  // The doctor associated with this availability.
}
