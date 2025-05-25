package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRecommendationResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientDTO;

@Component
public class DoctorRecommendationResponseMapperToDTO implements MapperToDTO<Recommendation , DoctorRecommendationResponseDTO> {
    @Autowired
    private  MapperToDTO<Patient, PatientDTO> patientMapper;
    @Override
    public DoctorRecommendationResponseDTO toDTO(Recommendation recommendation) {
        DoctorRecommendationResponseDTO dto = new DoctorRecommendationResponseDTO();
        dto.setRecommendation(recommendation.getDescripcion());
        dto.setPatient(patientMapper.toDTO(recommendation.getMedicalConsultation().getPatients()));
        return dto;
    }
}
