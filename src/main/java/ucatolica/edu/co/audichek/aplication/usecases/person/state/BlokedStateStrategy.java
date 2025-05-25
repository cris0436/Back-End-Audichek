package ucatolica.edu.co.audichek.aplication.usecases.person.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.person.ActivatePersonUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.person.DeactivatePerson;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

@Service("BLOCKED")
public class BlokedStateStrategy implements PersonStateStrategy{
    @Autowired
    @Lazy
    private DeactivatePerson deactivatePerson;
    @Autowired
    @Lazy
    private ActivatePersonUseCase activatePersonUseCase;

    @Override
    public String authenticatePerson(LoginRequestDTO person) {
        throw new BadRequestException("the person is blocked cannot authenticate");
    }

    @Override
    public String activatePerson(Person person) {
        return activatePersonUseCase.activatePerson(person);
    }

    @Override
    public String blockPerson(Person person) {
        return "this function is not implemented";
    }

    @Override
    public String inactivatePerson(Person person) {
        return deactivatePerson.deactivatePerson(person);
    }
}
