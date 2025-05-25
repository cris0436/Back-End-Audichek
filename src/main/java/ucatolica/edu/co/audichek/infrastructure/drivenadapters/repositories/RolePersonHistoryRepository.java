package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePersonHistory;

import java.util.List;
import java.util.Optional;

public interface RolePersonHistoryRepository extends JpaRepository<RolePersonHistory, Integer> {
    List<RolePersonHistory> findRolePermissionByIsActiveAndPerson(Boolean isActive, Person person);

    Optional<RolePersonHistory> findRolePersonHistoryByPersonAndIsActiveAndRole(Person person, Boolean isActive, Role role);
}
