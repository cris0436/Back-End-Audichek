package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePersonHistory;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonResponseDTO;
@Component
public class RolePersonResponseMapperToDTO implements MapperToDTO<RolePersonHistory,RolePersonResponseDTO> {
    @Override
    public RolePersonResponseDTO toDTO(RolePersonHistory rolePersonHistory) {
        RolePersonResponseDTO rolePersonResponseDTO = new RolePersonResponseDTO();
        rolePersonResponseDTO.setPersonId(rolePersonHistory.getPerson().getId());
        rolePersonResponseDTO.setRoleName(rolePersonHistory.getRole().getRoleName());
        return rolePersonResponseDTO;
    }


}
