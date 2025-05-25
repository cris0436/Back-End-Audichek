package ucatolica.edu.co.audichek.aplication.usecases.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePermission;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.RolePersonHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePermissionsRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.RolePersonHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.security.JwtUtilComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class AuthUseCases {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilComponent jwtUtil;
    private final RolePersonHistoryRepository rolePersonHistoryRepository;
    private final RolePermissionsRepository rolePermissionsRepository;
    private final PersonStatusHistoryRepository personStatusHistoryRepository;

    public String authenticate(LoginRequestDTO request) throws BadRequestException {

        Optional<Person> person = personRepository.findPersonById(request.getId());
        if (person.isEmpty() || !passwordEncoder.matches(request.getPassword(), person.get().getPassword())) {
            return "user or password incorrect";
        }

        List<RolePersonHistory> rolePerson = rolePersonHistoryRepository.findRolePermissionByIsActiveAndPerson(true, person.get());


        List<RolePermission> permission = new ArrayList<>();
        for (RolePersonHistory role : rolePerson) {
            List<RolePermission> rolePermissions =
                    rolePermissionsRepository.getRolePermissionByRole(role.getRole());
            if (!rolePermissions.isEmpty()) {
                permission.addAll(rolePermissions);
            }
        }
        List<PersonStatusHistory> personStatuses = personStatusHistoryRepository.findPersonStatusByPersonAndIsActive(person.get(),true);
        if (personStatuses.size() != 1) {
            throw new ConflictException("There was a problem trying to find the person's status, please try again later.");
        }
        
        return  jwtUtil.generateToken(person.get(), permission ,personStatuses.getFirst().getPersonStatus());
    }

}
