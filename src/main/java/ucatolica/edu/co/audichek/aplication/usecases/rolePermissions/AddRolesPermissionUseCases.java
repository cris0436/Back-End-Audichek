package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePermission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePermissionsRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RoleRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;

@Service
public class AddRolesPermissionUseCases {
    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    @Autowired
    private GetPermissionUseCases getPermissionUseCases;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FieldValidation<String> fieldValidationRolesPermissions;


    @Transactional
    public void addRolePermission(Role rol, Permission per){
        try{
            fieldValidationRolesPermissions.validateField(rol.getRoleName());
            fieldValidationRolesPermissions.validateField(per.getPermissionName());
            rol.setRoleName(fieldValidationRolesPermissions.sanitizeSnakeCase(rol.getRoleName()));
            per.setPermissionName(fieldValidationRolesPermissions.sanitizeSnakeCase(per.getPermissionName()));
            fieldValidationRolesPermissions.validateField(per.getPermissionName());
            rolePermissionsRepository.save(new RolePermission(rol, per));
        } catch (Exception e) {
            throw new ConflictException("The role already has this permission");
        }
    }

    public Role addRol(String roleName) {
        fieldValidationRolesPermissions.validateField(roleName);
        roleName = fieldValidationRolesPermissions.sanitizeSnakeCase(roleName);
        Role rol = roleRepository.findRoleByRoleName(roleName);
        if (rol == null){
            rol = new Role();
            rol.setRoleName(roleName);
            return roleRepository.save(rol);
        }
        return rol;
    }

    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @Transactional
    public String addRolePermissions(String role, List<String> permissionsNames) {

        String roleSanitized = fieldValidationRolesPermissions.sanitizeSnakeCase(role);
        fieldValidationRolesPermissions.validateField(roleSanitized);
        Role existingRol = roleRepository.findRoleByRoleName(roleSanitized);
        if (existingRol == null){
            throw new ConflictException("The role does not exist");
        }

        if (permissionsNames != null && !permissionsNames.isEmpty()) {
            for (String perName : permissionsNames) {

                String perNameSanitize = fieldValidationRolesPermissions.sanitizeSnakeCase(perName);
                fieldValidationRolesPermissions.validateField(perNameSanitize);
                Permission foundPermission = getPermissionUseCases.getPermissionDefault(perNameSanitize);
                List<RolePermission> rolePermission = rolePermissionsRepository.findRolePermissionsByRoleAndPermission(existingRol, foundPermission);
                if (rolePermission.isEmpty()) {
                    rolePermissionsRepository.save(new RolePermission(existingRol, foundPermission));
                }
            }
        }

        return "the role " + role + " has been added with the permissions";
    }


}
