package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findPermissionByPermissionName(String permissionName);
}
