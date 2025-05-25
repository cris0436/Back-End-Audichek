package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;



import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultationFeedbackResponseDTO {
    private String id;
    private String consultationId;
    private int rating;
    private String comments;
    private LocalDateTime creationDate;
}