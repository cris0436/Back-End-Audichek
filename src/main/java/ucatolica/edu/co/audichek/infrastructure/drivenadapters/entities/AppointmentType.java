package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class AppointmentType {
    @Id
    @GeneratedValue
    @Column(name = "appointment_type_id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false, length = 45)
    private String description;
}
