package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ucatolica.edu.co.audichek.domain.entities.AudiometryType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Audiometry {

    private UUID id;

    private Doctor doctor;

    private Patient patient;

    private Instant testDate;

    private Integer earScore;

    private String evaluatedEar;


    private AudiometryType audiometryType;
}