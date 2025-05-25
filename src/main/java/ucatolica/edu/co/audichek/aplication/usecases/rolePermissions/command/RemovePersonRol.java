package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonResponseDTO;

public class RemovePersonRol implements RolePermissionCommand<String> {
    private final RolePersonRequestDTO requestDTO;
    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;
    public RemovePersonRol(RolePersonRequestDTO requestDTO,ManageRolesPermissionsUseCases manageRolesPermissionsUseCases) {
        this.requestDTO = requestDTO;
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
    }

    @Override
    public String execute(){
        manageRolesPermissionsUseCases.removeRolPerson(
                requestDTO.getRoleName(),
                requestDTO.getPersonId()
        );
        return "Person now no have this rol";
    }

}
