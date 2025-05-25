package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "doctor_statuses")
public class DoctorStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_status_id", nullable = false)
    private Integer id;

    @Column(name = "Descripcion", nullable = false, length = 100)
    private String descripcion;

}