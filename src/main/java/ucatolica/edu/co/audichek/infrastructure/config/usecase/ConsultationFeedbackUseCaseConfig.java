package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.consultationFeedback.CreateConsultationFeedbackUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.ConsultationFeedbackMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationFeedbackRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalConsultationRepository;

@Configuration
public class ConsultationFeedbackUseCaseConfig {
    @Bean
    public CreateConsultationFeedbackUseCase createConsultationFeedbackUseCase(
            ConsultationFeedbackRepository feedbackRepository,
            MedicalConsultationRepository consultationRepository,
            ConsultationFeedbackMapper feedbackMapper) {

        return new CreateConsultationFeedbackUseCase(
                feedbackRepository,
                consultationRepository,
                feedbackMapper
        );
    }

}
