package ucatolica.edu.co.audichek.aplication.usecases.person.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.person.ActivatePersonUseCase;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

@Service("PENDING")
public class PendingStateStrategy implements PersonStateStrategy {

    @Autowired
    @Lazy
    private ActivatePersonUseCase ActivatePersonUseCase;
    @Override
    public String authenticatePerson(LoginRequestDTO person) {
       throw new ConflictException("This person is pending");
    }

    @Override
    public String activatePerson(Person existingPerson) {
        return ActivatePersonUseCase.personConfirm(existingPerson);
    }


    @Override
    public String blockPerson(Person person) {
       return  "This function is not implemented";
    }


    @Override
    public String inactivatePerson(Person person) {
        throw new BadRequestException("Cannot inactivate a pending person");
    }

}
