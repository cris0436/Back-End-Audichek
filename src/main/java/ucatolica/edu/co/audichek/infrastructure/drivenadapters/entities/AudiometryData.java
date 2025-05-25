package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiometry_data")
public class AudiometryData {

    @Id
    @GeneratedValue
    @Column(name = "audiometry_data_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "audiometry_id", nullable = false)
    private Audiometry audiometry;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "frequency_id", nullable = false)
    private AudiometryFrequency frequency;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "decibels_id", nullable = false)
    private AudiometryDecibel decibels;

    @Column(name = "test_date", nullable = false)
    private LocalDateTime testDate;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "audiometry_type_id", nullable = false)
    private AudiometryType audiometryType;
}
