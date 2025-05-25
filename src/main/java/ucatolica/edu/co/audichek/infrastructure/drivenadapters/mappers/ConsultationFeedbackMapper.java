package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;


import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationFeedback;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackResponseDTO;

import java.time.LocalDateTime;

@Component
public class ConsultationFeedbackMapper {

    public ConsultationFeedback toEntity(ConsultationFeedbackRequestDTO dto, MedicalConsultation consultation) {
        ConsultationFeedback feedback = new ConsultationFeedback();
        feedback.setMedicalConsultation(consultation);
        feedback.setRating(Integer.parseInt(dto.getRating()));
        feedback.setComments(dto.getComments());
        feedback.setCreationDate(LocalDateTime.now());
        return feedback;
    }

    public ConsultationFeedbackResponseDTO toResponseDTO(ConsultationFeedback feedback) {
        ConsultationFeedbackResponseDTO dto = new ConsultationFeedbackResponseDTO();
        dto.setId(feedback.getId().toString());
        dto.setConsultationId(feedback.getMedicalConsultation().getId().toString());
        dto.setRating(feedback.getRating());
        dto.setComments(feedback.getComments());
        dto.setCreationDate(feedback.getCreationDate());
        return dto;
    }
}
