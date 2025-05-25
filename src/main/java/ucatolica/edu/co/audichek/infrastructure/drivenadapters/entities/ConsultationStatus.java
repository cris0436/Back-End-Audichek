package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "consultation_statuses")
public class ConsultationStatus {
    @Id
    @GeneratedValue
    @Column(name = "consultation_status_id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

}
