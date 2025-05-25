package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class DoctorAvailability implements Serializable {
    public DoctorAvailability() {}
    public DoctorAvailability(LocalDate dateAvailability, LocalTime startTime, LocalTime endTime, Doctor doctor) {
        this.dateAvailability = dateAvailability;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;
        this.doctor = doctor;
    }
    private UUID id;  // ID of the availability record.

    private LocalDate dateAvailability;

    private LocalTime startTime;  // Start time of the availability.

    private LocalTime endTime;  // End time of the availability.

    private Boolean isAvailable;  // Boolean to indicate if the doctor is available during the time slot.

    // Adding the relationship to the Doctor entity
    private Doctor doctor;  // The doctor associated with this availability.
}