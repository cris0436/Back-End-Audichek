package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.AddRecommendationUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.patientRecommendation.GetRecommendationsUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RecommendationDTO;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST , RequestMethod.GET})
public class RecommendationController {
    @Autowired
    private AddRecommendationUseCase addRecommendationUseCase;
    @Autowired
    private GetRecommendationsUseCase getRecommendationsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recommendation addRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        return addRecommendationUseCase.addRecommendation(recommendationDTO);
    }


    @GetMapping("/get_my_recommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getMyRecommendations() {
        return getRecommendationsUseCase.getRecommendations();
    }

}