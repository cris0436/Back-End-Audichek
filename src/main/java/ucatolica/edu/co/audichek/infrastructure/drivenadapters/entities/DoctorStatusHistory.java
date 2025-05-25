package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "doctor_status_history")
public class DoctorStatusHistory implements Serializable {
    @Id
    @Column(name = "history_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "doctor_id")
    private Doctor doctor; // Relación unidireccional con Doctor

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_status_id", nullable = false)
    private DoctorStatus doctorStatus;

    @Column(name = "deta_availability", nullable = false)
    private Instant changeDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;
}
