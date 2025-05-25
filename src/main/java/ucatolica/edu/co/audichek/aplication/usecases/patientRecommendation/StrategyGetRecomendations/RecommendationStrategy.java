package ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.StrategyGetRecomendations;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

import java.util.List;

public interface RecommendationStrategy<T> {
    List<T> getRecommendations(Person existingPerson);
}
