package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DoctorResponseDTO {
    private UUID DoctorId;
    private String specialty;
    private PersonDTO person;
    private String professionalsLicense;
}
