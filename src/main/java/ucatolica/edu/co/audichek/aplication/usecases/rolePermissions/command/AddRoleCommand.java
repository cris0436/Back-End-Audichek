package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.AddRolesPermissionUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;

public class AddRoleCommand implements RolePermissionCommand<Role> {

    private final AddRolesPermissionUseCases manageRolesPermissionsUseCases;
    private final String role;

    public AddRoleCommand(AddRolesPermissionUseCases manageRolesPermissionsUseCases, String role) {
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
        this.role = role;
    }

    @Override
    public Role execute() {
        return manageRolesPermissionsUseCases.addRol(role);
    }
}
