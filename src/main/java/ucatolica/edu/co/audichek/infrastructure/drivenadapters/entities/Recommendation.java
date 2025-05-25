package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recommendations")
public class Recommendation {

    public Recommendation() {}
    public Recommendation (String descripcion ,MedicalConsultation medicalConsultation) {
        this.date = LocalDateTime.now();
        this.descripcion = descripcion;
        this.medicalConsultation = medicalConsultation;
    }
    @Id
    @GeneratedValue
    @Column(name = "recommendation_id", nullable = false)
    private UUID id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "recomendation_date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medical_consultation_id", nullable = false)
    private MedicalConsultation medicalConsultation;

}