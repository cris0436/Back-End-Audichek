package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiometry_decibels")
public class AudiometryDecibel {

    @Id
    @GeneratedValue
    @Column(name = "decibels_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "decibel_level", nullable = false)
    private Integer decibelLevel;
}