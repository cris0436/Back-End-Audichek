package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role> getRolesByRoleName(String roleName);

   Role findRoleByRoleName(String roleName);
}
