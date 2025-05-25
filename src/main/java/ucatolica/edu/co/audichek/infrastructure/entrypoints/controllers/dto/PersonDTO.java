package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PersonDTO {

    private String id;
    private String name;
    private String email;
    private UUID doctorId;
    private UUID patientId;
    private LocalDate birthDate;
    private String legalRepresentativePersonId;



}
