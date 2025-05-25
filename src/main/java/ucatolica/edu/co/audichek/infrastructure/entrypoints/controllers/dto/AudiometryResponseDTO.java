package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AudiometryResponseDTO {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private Instant testDate;
    private String evaluatedEar;
    private String audiometryType;
    private List<FrequencyDecibelPair> data;

    @Getter
    @Setter
    public static class FrequencyDecibelPair {
        private Integer frequencyHz;
        private Integer decibelLevel;
    }
}
