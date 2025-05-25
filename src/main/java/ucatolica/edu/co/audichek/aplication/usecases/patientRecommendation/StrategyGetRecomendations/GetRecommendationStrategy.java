package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.StrategyGetRecomendations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.ConflictException;

import java.util.List;
import java.util.Map;

@Service
public class GetRecommendationStrategy {
    private final Map<String, RecommendationStrategy<?>> strategies;

    @Autowired
    public GetRecommendationStrategy(Map<String, RecommendationStrategy<?>> strategies) {
        this.strategies = strategies;
    }

    public List<?> getRecommendationsForRole(String role, Person existingPerson) {
        RecommendationStrategy strategy = strategies.get(role.toUpperCase());
        if (strategy == null) {
            throw new ConflictException("No hay estrategia para el rol: " + role);
        }
        return strategy.getRecommendations(existingPerson);
    }
}
