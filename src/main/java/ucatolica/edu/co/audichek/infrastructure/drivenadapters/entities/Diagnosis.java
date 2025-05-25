package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "diagnoses")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "consultation_id", nullable = false)
    private MedicalConsultation consultation;

    @Column(name = "Descripcion", nullable = false)
    private String descripcion;

}