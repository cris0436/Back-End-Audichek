package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRecommendationResponseDTO {
    private String recommendation;
    private DoctorResponseDTO doctor;
}
