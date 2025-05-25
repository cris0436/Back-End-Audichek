package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class MedicalConsultation {
    public MedicalConsultation() {}
    public MedicalConsultation(Doctor doctor, String instructions, LocalDateTime consultationDate, Patient patients) {
        this.doctor = doctor;
        this.instructions = instructions;
        this.consultationDate = consultationDate;
        this.patients = patients;
    }
    private UUID id;

    private Doctor doctor;

    private String instructions;

    private LocalDateTime consultationDate;

    private Patient patients;

    private Appointment appointment;


}