package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue
    @Column( name = "doctor_id", nullable = false ,unique = true)  // Asegura que ambos compartan el mismo ID
    private UUID id;  // El id de Doctor es igual al id de Person


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialty_id", nullable = false)
    private DoctorSpecialty specialty;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false , unique = true)
    private Person person;

    @Column(name = "professionals_License", nullable = false , unique = true)
    private String professionalsLicense;
}
