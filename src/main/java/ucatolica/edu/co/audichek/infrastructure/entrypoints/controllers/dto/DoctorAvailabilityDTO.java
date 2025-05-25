package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAvailabilityDTO {
    private String id;
    private String doctorId;
    private String dateAvailability;
    private String startTime;
    private String endTime;

}
