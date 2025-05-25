package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MedicalBackgroundEntryDTO {
    private UUID id;
    private String description;
    private LocalDateTime createdAt;
}
