package ucatolica.edu.co.audichek.aplication.usecases.person.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.aplication.usecases.person.DeactivatePerson;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.aplication.usecases.auth.AuthUseCases;

@Service("ACTIVE")
public class ActiveStateStrategy implements PersonStateStrategy{

        @Autowired
        @Lazy
        private AuthUseCases authUseCases;
        @Autowired
        @Lazy
        private DeactivatePerson deactivatePerson;

        @Override
        public String authenticatePerson(LoginRequestDTO person) {
            return  authUseCases.authenticate(person);
        }

        @Override
        public String activatePerson(Person person) {
            throw new BadRequestException("This person is already active");
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
