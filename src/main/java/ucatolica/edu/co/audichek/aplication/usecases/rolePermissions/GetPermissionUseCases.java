package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PermissionRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;

@Service
public class GetPermissionUseCases {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private FieldValidation<String> fieldValidationRolesPermissions;

    public List<Permission> getPermission(){
        List<Permission> permission = permissionRepository.findAll();
        return permission;
    }

    public Permission getPermissionDefault(String name){
        String namePerSanitize = fieldValidationRolesPermissions.sanitizeSnakeCase(name);
        fieldValidationRolesPermissions.validateField(namePerSanitize);
        List<Permission> permission = permissionRepository.findPermissionByPermissionName(name);
        if (permission.isEmpty()){
            Permission newPermission = new Permission();
            newPermission.setPermissionName(name);
            return permissionRepository.save(newPermission );
        }
        return permission.getFirst();
    }
}
