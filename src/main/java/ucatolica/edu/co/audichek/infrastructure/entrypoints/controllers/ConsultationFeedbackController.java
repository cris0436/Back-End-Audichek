package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.consultationFeedback.CreateConsultationFeedbackUseCase;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.ConsultationFeedbackResponseDTO;

@RestController
@RequestMapping("/api/medical-consultations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST})
public class ConsultationFeedbackController {

    private final CreateConsultationFeedbackUseCase feedbackService;

    @PostMapping("/{consultationId}/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ConsultationFeedbackResponseDTO> createFeedback(
            @PathVariable String consultationId,
            @RequestBody ConsultationFeedbackRequestDTO feedbackRequest) {

        ConsultationFeedbackResponseDTO response = feedbackService.createFeedback(consultationId, feedbackRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
