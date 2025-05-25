package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequestDTO {
    private String doctorId;
    private String patientId;
    private String description;
    private String type;
    private String dateAvailability;
    private String startTime;
    private String endTime;

}
