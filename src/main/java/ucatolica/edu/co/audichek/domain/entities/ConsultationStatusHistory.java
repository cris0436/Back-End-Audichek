package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ConsultationStatusHistory {
    public ConsultationStatusHistory() {}
    public ConsultationStatusHistory(  MedicalConsultation medicalConsultation, ConsultationStatus estadosConsultaIdestadosConsulta) {
        this.changeDate = Instant.now();
        this.isActive = true;
        this.medicalConsultation = medicalConsultation;
        this.estadosConsultaIdestadosConsulta = estadosConsultaIdestadosConsulta;
    }
    private UUID id;


    private Instant changeDate;


    private Boolean isActive = false;

    private MedicalConsultation medicalConsultation ;

    private ConsultationStatus estadosConsultaIdestadosConsulta;

}