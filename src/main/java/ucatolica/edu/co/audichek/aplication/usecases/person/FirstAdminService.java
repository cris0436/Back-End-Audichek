package ucatolica.edu.co.audichek.aplication.usecases.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Admin;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AdminRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.aplication.usecases.person.state.PersonStateStrategy;
import ucatolica.edu.co.audichek.aplication.usecases.person.state.PersonStatesFactory;

import java.util.List;
import java.util.Optional;

@Service
public class FirstAdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonStatusHistoryUseCases personStatusHistoryService;
    @Autowired
    PersonStatesFactory personStatesFactory;


    public String createFirsAdmin(Admin admin){
        Optional<Person> person= personRepository.getPersonById(admin.getPerson().getId());
        if (person.isEmpty()) {
            throw new BadRequestException("Person not found");
        }
        admin.setPerson(person.get());
        Admin newAdmin = adminRepository.save(new Admin(person.get(), admin.getNotes()));
        person.get().setAdmin(newAdmin);

        return "first admin created and "+activateFirstAdmin(person.get().getId());
    }

    @Transactional
    public String activateFirstAdmin(String personId) {

        Person existingPerson = personRepository.findById(personId).orElse(null);
        if (existingPerson == null) {
            throw new BadRequestException("Person does not exist");
        }
        List<PersonStatusHistory> personStatusHistoryList = personStatusHistoryService.
                getStatusHistoryByIsActive(existingPerson);
        if (personStatusHistoryList.size() != 1) {
            throw new BadRequestException("person status history already do not exist");
        }
        PersonStateStrategy strategy = personStatesFactory.getStrategy(personStatusHistoryList.getFirst().getPersonStatus().getDescripcion());
        strategy.activatePerson(existingPerson);
        return "first admin activated";
    }
}
