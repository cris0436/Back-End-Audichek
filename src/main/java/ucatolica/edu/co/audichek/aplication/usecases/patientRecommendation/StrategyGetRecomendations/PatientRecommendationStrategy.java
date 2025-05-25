package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.StrategyGetRecomendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RecommendationRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientRecommendationResponseDTO;

import java.util.List;
@Component("PATIENT")
public class PatientRecommendationStrategy implements RecommendationStrategy<PatientRecommendationResponseDTO>{
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private MapperToDTO<Recommendation,PatientRecommendationResponseDTO>  mapperToDTO;


    @Override
    public List<PatientRecommendationResponseDTO> getRecommendations(Person existingPerson ) {
        List<Recommendation> recommendations = recommendationRepository.findRecommendationsByMedicalConsultation_Patients(existingPerson.getPatient());
        return recommendations.stream().map(mapperToDTO::toDTO).toList();
    }
}
