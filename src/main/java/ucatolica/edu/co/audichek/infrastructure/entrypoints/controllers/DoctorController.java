package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.AddDoctorUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.ConfirmedAppointmentUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.doctor.GetDoctorUseCase;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class DoctorController {
    private final GetDoctorUseCase getDoctorUseCase;
    private final AddDoctorUseCase addDoctorUseCase;
    private final ConfirmedAppointmentUseCase confirmedAppointmentUseCase;

    public DoctorController(GetDoctorUseCase getDoctorUseCase, AddDoctorUseCase addDoctorUseCase, ConfirmedAppointmentUseCase confirmedAppointmentUseCase) {
        this.getDoctorUseCase = getDoctorUseCase;
        this.addDoctorUseCase = addDoctorUseCase;
        this.confirmedAppointmentUseCase = confirmedAppointmentUseCase;
    }
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public DoctorResponseDTO addDoctor(@RequestBody DoctorRequestDTO doctor) {
        return addDoctorUseCase.addDoctor(doctor);
    }

    @PostMapping("/confirm_appointment/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String confirmAppointment(@PathVariable String appointmentId){
        return confirmedAppointmentUseCase.confirmAppointment(appointmentId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    private DoctorResponseDTO getDoctor (@PathVariable String id ){
        return getDoctorUseCase.getDoctor(id);
    }

    @GetMapping("/get_my_active_appointments")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    private List<AppointmentResponseDTO> getMyActiveAppointments(){
        return confirmedAppointmentUseCase.getMyPendingAppointments();
    }




}
