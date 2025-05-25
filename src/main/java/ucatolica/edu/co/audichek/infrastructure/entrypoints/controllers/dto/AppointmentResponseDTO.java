package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentResponseDTO {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private String description;
    private LocalDate dateAvailability;
    private LocalTime startTime;
    private LocalTime endTime;

}
