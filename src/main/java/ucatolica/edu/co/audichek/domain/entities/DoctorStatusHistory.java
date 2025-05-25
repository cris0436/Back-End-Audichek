package ucatolica.edu.co.audichek.domain.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class DoctorStatusHistory implements Serializable {
    private Integer id;

    private Doctor doctor; // Relación unidireccional con Doctor

    private DoctorStatus doctorStatus;

    private Instant changeDate;

    private Boolean isActive = false;
}