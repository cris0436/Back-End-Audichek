package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Recommendation {

    public Recommendation() {}
    public Recommendation (String descripcion ,MedicalConsultation medicalConsultation) {
        this.date = LocalDateTime.now();
        this.descripcion = descripcion;
        this.medicalConsultation = medicalConsultation;
    }
    private UUID id;

    private String descripcion;

    private LocalDateTime date;

    private MedicalConsultation medicalConsultation;

}