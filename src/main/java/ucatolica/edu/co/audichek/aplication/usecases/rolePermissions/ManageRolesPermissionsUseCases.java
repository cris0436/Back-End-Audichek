package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Role;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePersonHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePersonHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RoleRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.RolePersonResponseDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;
import java.util.Optional;

@Service
public class ManageRolesPermissionsUseCases {
    @Autowired
    private RolePersonHistoryRepository rolePersonHistoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FieldValidation<String> fieldValidationRolesPermissions;
    @Autowired
    private MapperToDTO<RolePersonHistory, RolePersonResponseDTO> mapperToDTO;
    @Autowired
    private GetRoleUseCase getRoleUseCase;

    @Transactional
    public RolePersonResponseDTO addRolePermissionHistory(String roleName, String personId ){
        personId = fieldValidationRolesPermissions.sanitize(personId);
        roleName = fieldValidationRolesPermissions.sanitizeSnakeCase(roleName);
        Role existingRol= getRoleUseCase.getRoleDefault(roleName);

        Optional<Person> existingPerson= personRepository.findPersonById(personId);
        if (existingPerson.isEmpty()){
            throw new BadRequestException("The person does not exist");
        }
        Optional<RolePersonHistory> personHistory =
                rolePersonHistoryRepository.findRolePersonHistoryByPersonAndIsActiveAndRole(existingPerson.get(),true,existingRol);
        if (personHistory.isPresent()) {
            return  mapperToDTO.toDTO(personHistory.get());
        }
        return mapperToDTO.toDTO(rolePersonHistoryRepository.save(
                new RolePersonHistory(existingRol,existingPerson.get())));

    }


    @Transactional
    public void removeRolPerson(String roleName,String personId ){
        personId = fieldValidationRolesPermissions.sanitize(personId);
        roleName = fieldValidationRolesPermissions.sanitize(roleName);
        fieldValidationRolesPermissions.validateField(roleName);
        fieldValidationRolesPermissions.validateField(personId);

        Optional<Person> person = personRepository.findPersonById(personId);
        if (person.isEmpty()){
            throw new BadRequestException("The person does not exist");
        }
        List<RolePersonHistory> rolePerson =
                rolePersonHistoryRepository.findRolePermissionByIsActiveAndPerson(true,person.get());
        if (rolePerson.isEmpty()){
            throw new ConflictException("The person has no active role");
        }
        for (RolePersonHistory rolePersonHistory : rolePerson) {
            if (rolePersonHistory.getRole().getRoleName().toLowerCase().equals(roleName.toLowerCase())){
                rolePersonHistory.setIsActive(false);
                rolePersonHistoryRepository.save(rolePersonHistory);
                return;
            }
        }
        throw new BadRequestException("The person has no active role '"+roleName+"'");
    }
}
