package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequestDTO {
    private String personId;
    private String specialty;
    private String professionalsLicense;
}
