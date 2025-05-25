package ucatolica.edu.co.audichek.aplication.usecases.consultationFeedback;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.ConsultationFeedback;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationFeedbackRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalConsultationRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.ConsultationFeedbackMapper;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackResponseDTO;

import java.util.UUID;


@RequiredArgsConstructor
public class CreateConsultationFeedbackUseCase {

    private final ConsultationFeedbackRepository feedbackRepository;
    private final MedicalConsultationRepository consultationRepository;
    private final ConsultationFeedbackMapper feedbackMapper;

    @Transactional
    public ConsultationFeedbackResponseDTO createFeedback(String consultationId, ConsultationFeedbackRequestDTO dto) {
        MedicalConsultation consultation = consultationRepository.findById(UUID.fromString(consultationId))
                .orElseThrow(() -> new BadRequestException("Consulta médica no encontrada."));

        int ratingParsed;
        try {
            ratingParsed = Integer.parseInt(dto.getRating());
        } catch (NumberFormatException e) {
            throw new BadRequestException("La valoración debe ser un número válido entre 1 y 5.");
        }

        if (ratingParsed < 1 || ratingParsed > 5) {
            throw new BadRequestException("La valoración debe estar entre 1 y 5.");
        }

        if (feedbackRepository.findByMedicalConsultation(consultation).isPresent()) {
            throw new BadRequestException("Ya existe un feedback para esta consulta.");
        }

        dto.setRating(String.valueOf(ratingParsed));
        ConsultationFeedback feedback = feedbackMapper.toEntity(dto, consultation);
        ConsultationFeedback saved = feedbackRepository.save(feedback);

        return feedbackMapper.toResponseDTO(saved);
    }
}
