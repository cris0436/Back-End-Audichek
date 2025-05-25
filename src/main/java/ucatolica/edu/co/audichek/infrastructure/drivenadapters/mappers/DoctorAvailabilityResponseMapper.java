package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;

@Component
public class DoctorAvailabilityResponseMapper implements  MapperToDTO< DoctorAvailability, DoctorAvailabilityDTO>{
    @Override
    public DoctorAvailabilityDTO  toDTO( DoctorAvailability entity) {
        DoctorAvailabilityDTO dto = new DoctorAvailabilityDTO();
        dto.setId(entity.getId().toString());
        dto.setDoctorId(entity.getDoctor().getId().toString());
        dto.setDateAvailability(entity.getDateAvailability().toString());
        dto.setStartTime(entity.getStartTime().toString());
        dto.setEndTime(entity.getEndTime().toString());
        return dto;
    }
}
