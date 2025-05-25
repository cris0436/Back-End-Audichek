package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "consultation_status_history")
public class ConsultationStatusHistory {
    public ConsultationStatusHistory() {}
    public ConsultationStatusHistory(  MedicalConsultation medicalConsultation, ConsultationStatus estadosConsultaIdestadosConsulta) {
        this.changeDate = Instant.now();
        this.isActive = true;
        this.medicalConsultation = medicalConsultation;
        this.estadosConsultaIdestadosConsulta = estadosConsultaIdestadosConsulta;
    }
    @Id
    @GeneratedValue
    @Column(name = "ID_Historial_Consulta", nullable = false)
    private UUID id;


    @Column(name = "change_date", nullable = false)
    private Instant changeDate;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "consultation_id", nullable = false)
    private MedicalConsultation medicalConsultation ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estados_consulta_idestados_consulta", nullable = false)
    private ConsultationStatus estadosConsultaIdestadosConsulta;

}
