package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "patient_id", nullable = false, unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;


    @Column(name = "medical_background", length = 500)
    private String medicalBackground;


}
