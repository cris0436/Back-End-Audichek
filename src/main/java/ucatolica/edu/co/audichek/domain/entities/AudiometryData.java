package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AudiometryData {

    private UUID id;

    private Audiometry audiometry;

    private AudiometryFrequency frequency;

    private AudiometryDecibel decibels;

    private LocalDateTime testDate;

    private AudiometryType audiometryType;
}