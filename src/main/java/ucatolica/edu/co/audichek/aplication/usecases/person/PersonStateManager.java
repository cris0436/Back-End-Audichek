package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.person.state.PersonStateStrategy;
import ucatolica.edu.co.audichek.aplication.usecases.person.state.PersonStatesFactory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;

import java.util.Optional;

@Service
public class PersonStateManager {

    private final PersonValidationUseCases personValidationUseCases;
    private final PersonRepository personRepository;
    private final PersonStatusHistoryUseCases personStatusHistoryService;
    private final PersonStatesFactory  personStatesFactory;

    @Autowired
    public PersonStateManager( PersonValidationUseCases personValidationUseCases,
                            PersonRepository personRepository,
                            PersonStatusHistoryUseCases personStatusHistoryService,
                               PersonStatesFactory personStatesFactory) {

        this.personValidationUseCases = personValidationUseCases;
        this.personRepository = personRepository;
        this.personStatusHistoryService = personStatusHistoryService;
        this.personStatesFactory = personStatesFactory;
    }
    public PersonStatus getStatePerson(Person existingPerson){
        return personStatusHistoryService.getStatusHistoryByIsActive(existingPerson)
                .stream().findFirst()
                .orElseThrow(() -> new BadRequestException("No active status history found for the person."))
                .getPersonStatus();

    }
    public Person getExistingPerson(String idPerson){
        personValidationUseCases.verifyID(idPerson);
        if (!personValidationUseCases.validatePerson(idPerson)) {
            throw new BadRequestException("Person is not valid");
        }

        Optional<Person> existingPerson = personRepository.findPersonById(idPerson);
        if (existingPerson.isEmpty()) {
            throw new BadRequestException("Person not found");
        }
        return existingPerson.get();
    }

    public String deactivatePerson(String idPerson){
        Person existingPerson = getExistingPerson(idPerson);
        PersonStatus getStatePerson = getStatePerson(existingPerson);
        PersonStateStrategy strategy = personStatesFactory.getStrategy(getStatePerson.getDescripcion());
        return strategy.inactivatePerson(existingPerson);

    }


    public String activatePerson(String idPerson){
        Person existingPerson = getExistingPerson(idPerson);
        PersonStatus getStatePerson = getStatePerson(existingPerson);
        PersonStateStrategy strategy = personStatesFactory.getStrategy(getStatePerson.getDescripcion());
        return strategy.activatePerson(existingPerson);
    }

    public String authPerson(LoginRequestDTO credentials){
        Person existingPerson = getExistingPerson(credentials.getId());
        PersonStatus getStatePerson = getStatePerson(existingPerson);
        PersonStateStrategy strategy = personStatesFactory.getStrategy(getStatePerson.getDescripcion());
        return strategy.authenticatePerson(credentials);
    }
}
