package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AppointmentTypesSpecialities {
    private UUID id;

    private AppointmentType appointmentType;

    private DoctorSpecialty specialties;
}