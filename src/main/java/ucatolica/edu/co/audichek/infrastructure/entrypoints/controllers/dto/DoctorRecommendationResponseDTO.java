package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRecommendationResponseDTO {
    private PatientDTO patient;
    private String recommendation;
}
