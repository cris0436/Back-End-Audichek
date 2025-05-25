package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PersonDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.LegalRepresentative;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.LegalRepresentativeRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;

import java.util.Optional;

@Service
public class AddPersonUseCases {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LegalRepresentativeRepository legalRepresentativeRepository;

    @Autowired
    PersonValidationUseCases personValidationUseCases;

    @Autowired
    PersonStatusHistoryUseCases personStatusHistoryService;

    @Autowired
    MapperToDTO<Person, PersonDTO> mapperToDTO;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AddPersonUseCases(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private Person addUnderagePerson(Person person) {
        Optional<Person> tutor = personRepository.getPersonById(person.getLegalRepresentative().getPerson().getId());
        if (tutor.isEmpty()) {
            throw new BadRequestException("Tutor does not exist (first the person have to create it to create a new underage person)");
        }
        if (personValidationUseCases.validateUnderAge(tutor.get())){
            throw new BadRequestException("Tutor is under age ");
        }
        if (person.getLegalRepresentative().getRelation() == null || person.getLegalRepresentative().getRelation().equals(" ")) {
            throw new BadRequestException("Tutor dont has a relation");
        }

        LegalRepresentative legalRepresentative =
                legalRepresentativeRepository.save(new LegalRepresentative(person.getLegalRepresentative().getRelation(),tutor.get()));

        person.setLegalRepresentative(legalRepresentative);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Person newPerson = personRepository.save(person);
        personStatusHistoryService.addPersonStatusHistory(newPerson,PersonStates.PENDING.toString());
        return newPerson;
    }

    @Transactional
    public PersonDTO addPerson(Person person) {
        personValidationUseCases.validateField(person);

        if (personValidationUseCases.validatePerson(person.getId())){
            throw new BadRequestException("Person already exists");
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        if (personValidationUseCases.validateUnderAge(person)){
            return mapperToDTO.toDTO(addUnderagePerson(person));
        }

        Person newPerson = personRepository.save(person);
        personStatusHistoryService.addPersonStatusHistory(newPerson,PersonStates.PENDING.toString());
        return mapperToDTO.toDTO(newPerson);

    }
}
