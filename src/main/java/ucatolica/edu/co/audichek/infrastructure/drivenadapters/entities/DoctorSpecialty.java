package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "doctor_specialties")
public class DoctorSpecialty {
    @Id
    @GeneratedValue
    @Column(name = "doctor_specialty_id", nullable = false)
    private UUID id;

    @Column(name = "specialty", nullable = false, length = 45)
    private String specialty;

}
