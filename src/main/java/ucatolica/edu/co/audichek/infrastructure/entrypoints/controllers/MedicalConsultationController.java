package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation.AddMedicalConsultationUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.MedicalConsultationDTO;

@RestController
@RequestMapping("/api/medical_consultations")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class MedicalConsultationController {
    private final AddMedicalConsultationUseCase addMedicalConsultationUseCase;

    public MedicalConsultationController(AddMedicalConsultationUseCase addMedicalConsultationUseCase) {
        this.addMedicalConsultationUseCase = addMedicalConsultationUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalConsultation createMedicalConsultation(@RequestBody MedicalConsultationDTO medicalConsultationDTO){
        return  addMedicalConsultationUseCase.addMedialConsultation(medicalConsultationDTO);
    }
}
