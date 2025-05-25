package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalConsultationDTO {

    private String doctorId;
    private String instructions;
    private String consultationDate;
    private String patientId;
}
