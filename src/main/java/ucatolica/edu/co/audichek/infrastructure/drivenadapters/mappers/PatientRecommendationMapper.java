package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Recommendation;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientRecommendationResponseDTO;
@Component
public class PatientRecommendationMapper implements MapperToDTO<Recommendation, PatientRecommendationResponseDTO>{
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public PatientRecommendationResponseDTO toDTO(Recommendation entity) {
        PatientRecommendationResponseDTO dto = new PatientRecommendationResponseDTO();
        dto.setRecommendation(entity.getDescripcion());
        dto.setDoctor(doctorMapper.toDTO(entity.getMedicalConsultation().getDoctor()));
        return null;
    }
}
