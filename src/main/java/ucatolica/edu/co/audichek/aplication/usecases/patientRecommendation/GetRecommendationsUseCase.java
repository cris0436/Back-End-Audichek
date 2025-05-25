package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.StrategyGetRecomendations.GetRecommendationStrategy;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
@Service
public class GetRecommendationsUseCase {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GetRecommendationStrategy getRecommendationStrategy;

    public List<?> getRecommendations(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<Person> existingPerson  = personRepository.findPersonById(userId);
        if (existingPerson.isEmpty()) {
            throw new ConflictException("The person does not exist");}
        if (existingPerson.get().getDoctor() != null){
            return getRecommendationStrategy.getRecommendationsForRole("DOCTOR", existingPerson.get());
        }
        if (existingPerson.get().getPatient() != null){
            return getRecommendationStrategy.getRecommendationsForRole("PATIENT", existingPerson.get());
        }
        return null;
    }
}
