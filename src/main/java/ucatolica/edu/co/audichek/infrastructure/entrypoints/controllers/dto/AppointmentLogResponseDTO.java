package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentLogResponseDTO {
    String action;
    String userId;
    LocalDateTime timestamp;
    AppointmentResponseDTO previousAppointment ;
    AppointmentResponseDTO newAppointment;
}
