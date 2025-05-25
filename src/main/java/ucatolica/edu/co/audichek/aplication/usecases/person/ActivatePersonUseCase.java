package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.GetRoleUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.Roles;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivatePersonUseCase {
    private final PersonStatusHistoryUseCases personStatusHistoryService;
    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;
    private final PersonStatusHistoryRepository personStatusHistoryRepository;
    private final GetRoleUseCase getRoleUseCase;
    private final PersonStatusRepository personStatusRepository;

    public ActivatePersonUseCase(
            PersonStatusHistoryUseCases personStatusHistoryService,
            ManageRolesPermissionsUseCases manageRolesPermissionsUseCases,
            PersonStatusHistoryRepository personStatusHistoryRepository,
            GetRoleUseCase getRoleUseCase,
            PersonStatusRepository personStatusRepository
    ) {
        this.personStatusHistoryService = personStatusHistoryService;
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
        this.personStatusHistoryRepository = personStatusHistoryRepository;
        this.getRoleUseCase = getRoleUseCase;
        this.personStatusRepository = personStatusRepository;
    }


    public String activatePerson(Person existingPerson) {
        List<Roles> roles =  new ArrayList<>();

        // Obtener historial de estado activo
        Optional<PersonStatusHistory> activeStatus = personStatusHistoryRepository
                .findPersonStatusByPersonAndIsActive(existingPerson, true)
                .stream().findFirst();

        if (activeStatus.isEmpty()) {
            throw new ConflictException("Person status history does not exist");
        }

        activeStatus.get().setIsActive(false);
        personStatusHistoryRepository.save(activeStatus.get());


        PersonStatus inactivePersonStatus = personStatusRepository
                .findByDescripcion(PersonStates.INACTIVE.toString())
                .orElseGet(() -> personStatusRepository.save(new PersonStatus(PersonStates.ACTIVE.toString())));

        personStatusHistoryRepository.save(new PersonStatusHistory(inactivePersonStatus, existingPerson));

        return "Person successfully Activated.";
    }

    public String personConfirm(Person existingPerson) {
        List<Roles> roles =  new ArrayList<>();

        if (existingPerson.getDoctor() != null) {
            roles.add(Roles.DOCTOR);
        }
        if (existingPerson.getAdmin() != null) {
            roles.add(Roles.ADMIN);
        }
        if (existingPerson.getPatient() != null) {
            roles.add(Roles.PATIENT);
        }
        if (roles.isEmpty()) {
            throw new BadRequestException("this person has no roles");
        }


        for (Roles role : roles) {
                manageRolesPermissionsUseCases.addRolePermissionHistory(role.toString(),existingPerson.getId());
            }
        try {
            List<PersonStatusHistory> personStatusHistoriesList = personStatusHistoryService.
                    getStatusHistoryByStatusAndIsActive(existingPerson ,PersonStates.PENDING.toString());

            if (personStatusHistoriesList.size() != 1) {
                throw new BadRequestException("person status history already do not exist");
            }

            personStatusHistoriesList.getFirst().setIsActive(false);
            personStatusHistoryRepository.save(personStatusHistoriesList.getFirst());
            personStatusHistoryService.addPersonStatusHistory(existingPerson ,PersonStates.ACTIVE.toString());
        }
        catch (Exception e) {
            throw new BadRequestException("Error activating the person");
        }

        return "person is confirmed successfully";
    }

}
