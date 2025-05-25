package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "appointment_types_specialities")
public class AppointmentTypesSpecialities {
    @Id
    @GeneratedValue
    @Column(name = "appointment_type_speciality_id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", nullable = false)
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private DoctorSpecialty specialties;
}
