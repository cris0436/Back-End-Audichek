package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiometries")
public class Audiometry {

    @Id
    @GeneratedValue
    @Column(name = "audiometry_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "patient_id")
    private Patient patient;

    @Column(name = "test_date", nullable = false)
    private Instant testDate;

    @Column(name = "ear_score", nullable = false)
    private Integer earScore;

    @Column(name = "evaluated_ear", nullable = false)
    private String evaluatedEar;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "audiometry_type_id", nullable = false)
    private AudiometryType audiometryType;
}
