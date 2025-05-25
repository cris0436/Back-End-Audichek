package ucatolica.edu.co.audichek.domain.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConsultationStatus {
    private UUID id;

    private String description;

}