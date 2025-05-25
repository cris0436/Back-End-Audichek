package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiometry_frequencies")
public class AudiometryFrequency {

    @Id
    @GeneratedValue
    @Column(name = "frequency_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "frequency_hz", nullable = false)
    private Integer frequencyHz;
}