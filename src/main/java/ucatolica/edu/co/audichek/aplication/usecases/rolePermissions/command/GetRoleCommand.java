package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.GetRoleUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;

public class GetRoleCommand implements RolePermissionCommand<Role> {
    private final GetRoleUseCase manageRolesPermissionsUseCases;
    private final String role;

    public GetRoleCommand(GetRoleUseCase manageRolesPermissionsUseCases, String role) {
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
        this.role = role;
    }
    @Override
    public Role execute() {
        return manageRolesPermissionsUseCases.getRole(role);
    }
}
