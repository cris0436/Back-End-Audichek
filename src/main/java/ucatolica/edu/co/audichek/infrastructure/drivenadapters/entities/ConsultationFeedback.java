package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "consultation_feedbacks")
public class ConsultationFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_feedback_id", nullable = false)
    private UUID id;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "consultation_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MedicalConsultation medicalConsultation;
}