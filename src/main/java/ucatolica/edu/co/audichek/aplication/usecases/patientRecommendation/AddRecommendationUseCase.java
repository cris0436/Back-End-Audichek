package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.ConsultationStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalConsultationRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RecommendationRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RecommendationDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddRecommendationUseCase {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private FieldValidation fieldValidationRolesPermissions;

    @Autowired
    private MedicalConsultationRepository medicalConsultationRepository;
    @Autowired
    private ConsultationStatusHistoryRepository consultationStatusHistoryRepository;

    public Recommendation addRecommendation(RecommendationDTO recommendationDTO){

        String description=fieldValidationRolesPermissions.sanitize(recommendationDTO.getRecommendation());
        fieldValidationRolesPermissions.validateUUID(recommendationDTO.getMedicalConsultationId());
        if (recommendationDTO.getRecommendation().length()<=200){
            throw new BadRequestException("Recommendation is too short");
        }
        Optional<MedicalConsultation> existingMedicalConsultation = medicalConsultationRepository.findById(UUID.fromString(recommendationDTO.getMedicalConsultationId()));

        if (existingMedicalConsultation.isPresent()){
            return recommendationRepository.save(new Recommendation(description,existingMedicalConsultation.get()));
        }
        throw new BadRequestException("Medical Consultation does not exist");

    }
}
