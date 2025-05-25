package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ConsultationFeedback {

    private UUID id;

    private int rating;

    private String comments;

    private LocalDateTime creationDate;

    private MedicalConsultation medicalConsultation;
}