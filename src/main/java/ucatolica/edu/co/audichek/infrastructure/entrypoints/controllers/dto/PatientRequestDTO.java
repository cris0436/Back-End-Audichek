package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDTO {
    private String medicalBackground;
    private String personId;
}
