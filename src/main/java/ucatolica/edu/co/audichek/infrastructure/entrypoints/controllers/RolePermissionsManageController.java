package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.patient.GetPatientUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.*;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.command.*;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Permission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.DoctorMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.PatientMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.*;

import java.util.List;

@RestController
@RequestMapping("/api/role_permissions")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RequiredArgsConstructor
public class RolePermissionsManageController {

    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;
    private final GetPermissionUseCases getPermissionUseCases;
    private final GetRoleUseCase getRoleUseCase;
    private final AddRolesPermissionUseCases addRolesPermissionUseCases;


    @GetMapping("/rol/{rolName}")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(HttpStatus.OK)
    public Role getRol(@PathVariable String rolName){
            RolePermissionCommand<Role> command = new GetRoleCommand(getRoleUseCase,rolName);
            return command.execute();
    }

    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(HttpStatus.OK)
    public List<Permission> getPermissions(){
        RolePermissionCommand<List<Permission>> command = new GetPermissionsCommand(getPermissionUseCases);
        return command.execute();
    }

    @PostMapping("/add_rol")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public Role addRol(@RequestBody RolePersonRequestDTO role){
        RolePermissionCommand<Role> command = new AddRoleCommand(addRolesPermissionUseCases,role.getRoleName());
        return command.execute();
    }

    @PostMapping("/add_permission_to_rol")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(HttpStatus.OK)
    public String addPermissionsToRol(@RequestBody RolePermissionsRequestDTO rolePermissionsRequestDTO){
        RolePermissionCommand<String> command = new AddPermissionsRolCommand(rolePermissionsRequestDTO,addRolesPermissionUseCases);
        return command.execute();
    }

    @PostMapping("/add_rol_to_person")
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public RolePersonResponseDTO addPersonRol(@RequestBody RolePersonRequestDTO rolePersonRequestDTO){
        RolePermissionCommand<RolePersonResponseDTO> command = new AddRolPersonCommand(rolePersonRequestDTO,manageRolesPermissionsUseCases);
        return command.execute();
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('MANAGE_PERMISSIONS')")
    @ResponseStatus(HttpStatus.OK)
    public String removePersonRol(@RequestBody  RolePersonRequestDTO rolePersonRequestDTO){
        RolePermissionCommand<String> command = new RemovePersonRol(rolePersonRequestDTO,manageRolesPermissionsUseCases);
        return command.execute();
    }


}
