package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonResponseDTO;

public class AddRolPersonCommand implements RolePermissionCommand<RolePersonResponseDTO> {
    private final RolePersonRequestDTO requestDTO;
    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;

    public AddRolPersonCommand(RolePersonRequestDTO requestDTO, ManageRolesPermissionsUseCases manageRolesPermissionsUseCases) {
        this.requestDTO = requestDTO;
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
    }
    public RolePersonResponseDTO execute() {
        return manageRolesPermissionsUseCases.addRolePermissionHistory(
                requestDTO.getRoleName(),
                requestDTO.getPersonId()
        );
    }
}
