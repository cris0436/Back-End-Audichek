package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.AddRolesPermissionUseCases;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePermissionsRequestDTO;

public class AddPermissionsRolCommand implements RolePermissionCommand<String> {
    private final RolePermissionsRequestDTO rolePermissionsRequestDTO;
    private final AddRolesPermissionUseCases addRolesPermissionUseCases;

    public AddPermissionsRolCommand(RolePermissionsRequestDTO rolePermissionsRequestDTO, AddRolesPermissionUseCases addRolesPermissionUseCases) {
        this.rolePermissionsRequestDTO = rolePermissionsRequestDTO;
        this.addRolesPermissionUseCases = addRolesPermissionUseCases;
    }

    @Override
    public String execute(){
        return addRolesPermissionUseCases.addRolePermissions(
                rolePermissionsRequestDTO.getRole(),
                rolePermissionsRequestDTO.getPermissions()
        );
    }
}
