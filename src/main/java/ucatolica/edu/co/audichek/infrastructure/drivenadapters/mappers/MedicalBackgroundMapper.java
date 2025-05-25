package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalBackgroundEntry;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.MedicalBackgroundEntryDTO;

public class MedicalBackgroundMapper {
    public static MedicalBackgroundEntryDTO toDTO(MedicalBackgroundEntry entry) {
        MedicalBackgroundEntryDTO dto = new MedicalBackgroundEntryDTO();
        dto.setId(entry.getId());
        dto.setDescription(entry.getDescription());
        dto.setCreatedAt(entry.getCreatedAt());
        return dto;
    }
}
