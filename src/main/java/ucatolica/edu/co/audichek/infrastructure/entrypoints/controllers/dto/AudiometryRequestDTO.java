package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.events.Event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AudiometryRequestDTO {
    private String doctorId;
    private String patientId;
    private Instant testDate;
    private String evaluatedEar;
    private String audiometryType;
    private List<FrequencyDecibelPair> data;

    @lombok.Data
    public static class FrequencyDecibelPair {
        private Integer frequencyHz;
        private Integer decibelLevel;
    }
}
