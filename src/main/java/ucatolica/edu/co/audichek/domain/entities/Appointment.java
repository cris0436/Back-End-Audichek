package ucatolica.edu.co.audichek.domain.entities;

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
public class Appointment implements Serializable {

    private UUID id;

    private Doctor doctor;

    private Patient patient;

    private LocalTime startTime;  // Start time of the availability.

    private LocalTime endTime;  // End time of the availability.

    private LocalDate dateAppointment;

    private AppointmentType appointmentType;

    private String description;


}