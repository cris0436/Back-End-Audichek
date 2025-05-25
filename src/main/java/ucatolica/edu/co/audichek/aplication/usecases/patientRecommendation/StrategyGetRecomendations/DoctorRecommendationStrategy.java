package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.StrategyGetRecomendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RecommendationRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRecommendationResponseDTO;

import java.util.List;

@Component("DOCTOR")
public class DoctorRecommendationStrategy implements RecommendationStrategy<DoctorRecommendationResponseDTO> {
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private MapperToDTO<Recommendation , DoctorRecommendationResponseDTO> mapperToDTO;

    @Override
    public List<DoctorRecommendationResponseDTO> getRecommendations(Person existingPerson) {
        List<Recommendation> recommendations = recommendationRepository.findRecommendationsByMedicalConsultation_Doctor(existingPerson.getDoctor());
        if (recommendations.isEmpty()) {
            throw new BadRequestException("recommendations not found");
        }
        return recommendations.stream().map(mapperToDTO::toDTO).toList();
    }
}