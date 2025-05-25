package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePermission;

import java.util.List;

public interface RolePermissionsRepository extends JpaRepository <RolePermission,Integer> {

    List<RolePermission> getRolePermissionByRole(Role role);

    List<RolePermission> findRolePermissionsByRoleAndPermission(Role role, Permission permission);
}
