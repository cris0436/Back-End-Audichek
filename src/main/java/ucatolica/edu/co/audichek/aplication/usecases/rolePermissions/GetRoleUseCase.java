package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RoleRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;
import java.util.Optional;

@Service
public class GetRoleUseCase {
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private GetPermissionUseCases getPermissionUseCases;
    @Autowired
    private AddRolesPermissionUseCases addRolesPermissionUseCases;
    @Autowired
    private FieldValidation<String> fieldValidationRolesPermissions;

    public Role getRole(String role) {
        role = fieldValidationRolesPermissions.sanitizeSnakeCase(role);
        fieldValidationRolesPermissions.validateField(role);
        Optional<Role> existingRole = roleRepository.getRolesByRoleName(role);
        if (existingRole.isEmpty()){
            throw new BadRequestException("The role does not exist");
        }
        return existingRole.get();
    }

    @Transactional
    public Role getRoleDefault(String role) {
        role = fieldValidationRolesPermissions.sanitizeSnakeCase(role);
        fieldValidationRolesPermissions.validateField(role);
        Roles rol = Roles.valueOf(role);
        Optional<Role> existingRole = roleRepository.getRolesByRoleName(role);
        if (existingRole.isEmpty()){
            Role newRole = new Role();
            newRole.setRoleName(role);
            Role newRol=roleRepository.save(newRole);
            List<String> permissions = Permissions.rolePermissions(rol);
            for (String permission : permissions){
                addRolesPermissionUseCases.addRolePermission(newRol, getPermissionUseCases.getPermissionDefault(permission)) ;
            }
            return newRole;

        }
        return existingRole.get();
    }
}
