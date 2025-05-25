package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PersonDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;

import java.util.Optional;

@Service
public class UpdatePerson {
    private final PersonRepository personRepository;
    private final PersonValidationUseCases personValidationUseCases;
    private final MapperToDTO<Person, PersonDTO> mapperToDTO;

    public UpdatePerson(PersonRepository personRepository, PersonValidationUseCases personValidationUseCases, MapperToDTO<Person, PersonDTO> mapperToDTO) {
        this.personRepository = personRepository;
        this.personValidationUseCases = personValidationUseCases;
        this.mapperToDTO = mapperToDTO;
    }

    public PersonDTO updatePerson(String existingPersonId, Person person){
        Optional<Person> personOptional = personRepository.findById(existingPersonId);
        if (personOptional.isEmpty()) {
            throw new RuntimeException("Person not found");
        }
        if (personOptional.get().getBirthDate() != person.getBirthDate()){
            throw new RuntimeException("The birth date cannot be changed");
        }
        personValidationUseCases.validateField(person);
        person.setId(existingPersonId);

        Person savedPerson = personRepository.save(person);
        return mapperToDTO.toDTO(savedPerson);
    }


}
