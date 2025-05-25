package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiometry_types")
public class AudiometryType implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "audiometry_type_id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false, length = 45)
    private String description;


}
