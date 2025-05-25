package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AppointmentController {

    private final GetAppointmentUseCases getAppointmentUseCases;
    private final AddAppointmentUseCases addAppointmentUseCases;
    private final ConfirmAppointmentUseCases confirmAppointmentUseCases;
    private final RescheduleAppointmentUseCases rescheduleAppointmentUseCases;
    private final CancelAppointmentUseCases cancelAppointmentUseCases;
    private final GetAppointmentAudiLog getAppointmentAudiLog;

    @Autowired
    public AppointmentController(GetAppointmentUseCases getAppointmentUseCases, AddAppointmentUseCases addAppointmentUseCases, ConfirmAppointmentUseCases confirmAppointmentUseCases, RescheduleAppointmentUseCases rescheduleAppointmentUseCases, CancelAppointmentUseCases cancelAppointmentUseCases,GetAppointmentAudiLog getAppointmentAudiLog) {
        this.getAppointmentUseCases = getAppointmentUseCases;
        this.addAppointmentUseCases = addAppointmentUseCases;
        this.confirmAppointmentUseCases = confirmAppointmentUseCases;
        this.rescheduleAppointmentUseCases = rescheduleAppointmentUseCases;
        this.cancelAppointmentUseCases = cancelAppointmentUseCases;
        this.getAppointmentAudiLog = getAppointmentAudiLog;
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return getAppointmentUseCases.getAllAppointments();
    }

    @GetMapping("/getLog")
    public List<AppointmentLogResponseDTO> getAppointmentAudiLog(){
        return getAppointmentAudiLog.getAppointmentAudiLog();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public AppointmentResponseDTO getAppointmentById(@PathVariable String id) {
        return getAppointmentUseCases.getAppointmentById(id);
    }


    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentRequestDTO appointment) {
        return addAppointmentUseCases.addAppointment(appointment);
    }
    @PutMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public AppointmentResponseDTO putAppointment(@PathVariable String id , @RequestBody AppointmentRequestDTO appointment) {
        return rescheduleAppointmentUseCases.rescheduleAppointment(id ,appointment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public String dalAppointment(@PathVariable String id) {
        return cancelAppointmentUseCases.appointmentCancel(id);
    }

    @PostMapping("/confirm-appointment/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public String confirmAppointment(@PathVariable Integer id) {
        return confirmAppointmentUseCases.confirmAppointment(id);
    }



}
