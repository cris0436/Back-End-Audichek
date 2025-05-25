package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command;

import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.GetPermissionUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;

import java.util.List;

public class GetPermissionsCommand implements RolePermissionCommand<List<Permission>> {
    private final GetPermissionUseCases getPermissionUseCases;

    public GetPermissionsCommand(GetPermissionUseCases getPermissionUseCases) {
        this.getPermissionUseCases = getPermissionUseCases;
    }

    public List<Permission> execute() {
        return getPermissionUseCases.getPermission();
    }
}
