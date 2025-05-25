package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientDTO;
import ucatolica.edu.co.audichek.aplication.usecases.patient.AddPatientUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.patient.GetPatientUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PatientController {

    private final AddPatientUseCases addPatientUseCases;
    private final GetPatientUseCases getPatientUseCases;
    PatientController(AddPatientUseCases addPatientUseCases, GetPatientUseCases getPatientUseCases) {
        this.addPatientUseCases = addPatientUseCases;
        this.getPatientUseCases = getPatientUseCases;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDTO> getAllPatients(){
        return getPatientUseCases.getPatient();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO getPatientById(@PathVariable String id) {
        return getPatientUseCases.getPatientById(id);
    }

    @GetMapping("/self_info")
    @ResponseStatus(HttpStatus.OK)
    private PatientDTO getProfilePatient() {
        return getPatientUseCases.getSelfPatient();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO createPatient(@RequestBody PatientRequestDTO patient) {
        return addPatientUseCases.addPatient(patient);
    }
}
