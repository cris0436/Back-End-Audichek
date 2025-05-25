package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PatientDTO {

    private UUID patientId;
    private String personId;
    private String medicalBackground;
    private String name;
    private String email;
    private LocalDate birthDate;
    private String legalRepresentativeIdPerson;
    private String legalRepresentativeRelation;
}
