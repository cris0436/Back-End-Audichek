package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MedicalBackgroundEntry {

    private UUID id;

    private Patient patient;

    private String description;

    private LocalDateTime createdAt;
}