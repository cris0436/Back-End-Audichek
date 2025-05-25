package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.GetAvailability;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.DoctorAvailabilityUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;

import java.util.List;


@RestController
@RequestMapping("/api/doctor_availabilities")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DoctorAvailabiliryController {

    private final DoctorAvailabilityUseCases doctorAvailabilityUseCases;
    private final GetAvailability getAvailability;
    @Autowired
    public DoctorAvailabiliryController(DoctorAvailabilityUseCases doctorAvailabilityUseCases, GetAvailability getAvailability) {
        this.doctorAvailabilityUseCases = doctorAvailabilityUseCases;
        this.getAvailability = getAvailability;
    }

    @GetMapping("/typeApp/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<DoctorAvailabilityDTO> getAllDoctorAvailabilitiesEntity(@PathVariable String type) {
        return getAvailability.getDoctorsAvailabilityByAppoitmentType(type);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DoctorAvailabilityDTO> getAllDoctorAvailabilities() {
        return doctorAvailabilityUseCases.getAllDoctorAvailabilities();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DoctorAvailabilityDTO getDoctorAvailabilityById(@PathVariable String id) {
        return doctorAvailabilityUseCases.getDoctorAvailabilityById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorAvailabilityDTO createDoctorAvailability(@RequestBody DoctorAvailabilityDTO doctorAvailabilityDTO) {
        return doctorAvailabilityUseCases.addDoctorAvailability(doctorAvailabilityDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorAvailabilityDTO updateDoctorAvailability(@PathVariable String id, @RequestBody DoctorAvailabilityDTO doctorAvailability) {
        return doctorAvailabilityUseCases.updateDoctorAvailability(id, doctorAvailability);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorAvailability(@PathVariable String id) {
        doctorAvailabilityUseCases.deleteDoctorAvailability(id);
    }
}
