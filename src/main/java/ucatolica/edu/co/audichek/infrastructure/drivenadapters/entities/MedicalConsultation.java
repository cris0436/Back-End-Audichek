package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "medical_consultations")
public class MedicalConsultation {
    public MedicalConsultation() {}
    public MedicalConsultation(Doctor doctor, String instructions, LocalDateTime consultationDate, Patient patients) {
        this.doctor = doctor;
        this.instructions = instructions;
        this.consultationDate = consultationDate;
        this.patients = patients;
    }
    @Id
    @GeneratedValue
    @Column(name = "consultation_id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "doctor_id")
    private Doctor doctor;

    @Column(name = "instructions" , nullable = true)
    private String instructions;

    @Column(name = "consultation_date", nullable = false)
    private LocalDateTime consultationDate;

    @ManyToOne(optional = false)
    private Patient patients;

    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;


}