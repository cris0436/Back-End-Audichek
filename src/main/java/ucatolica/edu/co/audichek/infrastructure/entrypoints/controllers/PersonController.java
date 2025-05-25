package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.person.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PersonDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

@RestController
@RequestMapping("/api/persons")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST , RequestMethod.PUT})
public class PersonController {
    private final AddPersonUseCases addPersonUseCases;
    private final ActivatePersonUseCase activatePersonUseCase;
    private final UpdatePerson updatePerson;
    private final PersonStateManager personStateManager;

    public  PersonController(AddPersonUseCases addPersonUseCases, ActivatePersonUseCase activatePersonUseCase, UpdatePerson updatePerson , PersonStateManager personStateManager) {
        this.addPersonUseCases = addPersonUseCases;
        this.activatePersonUseCase = activatePersonUseCase;
        this.updatePerson = updatePerson;
        this.personStateManager = personStateManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO addPerson(@RequestBody Person person) {
        return addPersonUseCases.addPerson(person);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO updatePerson(@PathVariable String id, @RequestBody Person person) {
        return updatePerson.updatePerson(id,person);
    }

    @PostMapping("/confirm/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String confirmPerson(@PathVariable String id) {

        return (personStateManager.activatePerson(id));
    }
}
