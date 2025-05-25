package ucatolica.edu.co.audichek.aplication.usecases.person.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.aplication.usecases.person.ActivatePersonUseCase;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;

@Component("INACTIVE")
public class InactiveStateStrategy implements PersonStateStrategy{
    @Autowired
    @Lazy
    private ActivatePersonUseCase activatePersonUseCase;

    @Override
    public String authenticatePerson(LoginRequestDTO person) {
        return "";
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
        throw new BadRequestException("this person is already inactive");
    }
}
