package ucatolica.edu.co.audichek.aplication.usecases.person.state;


import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

public interface PersonStateStrategy {

     String authenticatePerson(LoginRequestDTO person);
     String activatePerson(Person person);
     String blockPerson(Person person);
     String inactivatePerson(Person person);



}
