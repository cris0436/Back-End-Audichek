package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusRepository;

import java.util.List;

@Service
public class PersonStatusHistoryUseCases {

    @Autowired
    PersonStatusRepository personStatusRepository;

    @Autowired
    PersonStatusHistoryRepository personStatusHistoryRepository;


    public List<PersonStatusHistory> getStatusHistoryByStatusAndIsActive(Person person , String status) {
        return personStatusHistoryRepository.
                findPersonStatusHistoriesByPersonAndPersonStatusAndIsActive(person,getPersonStatus(PersonStates.PENDING.toString()),true);
    }

    public List<PersonStatusHistory> getStatusHistoryByIsActive(Person person) {
        return personStatusHistoryRepository.
                getPersonStatusHistoriesByPersonAndIsActive(person,true);
    }

    @Transactional
    public PersonStatusHistory addPersonStatusHistory(Person person , String status) {
        return personStatusHistoryRepository.
                save(new PersonStatusHistory(getPersonStatus(status),person));
    }

    @Transactional
    public PersonStatus getPersonStatus(String descripcion) {
        return personStatusRepository.findByDescripcion(descripcion)
                .orElseGet(() -> {
                    PersonStatus newPersonStatus= new PersonStatus(descripcion);
                    return personStatusRepository.save(newPersonStatus);
                });
    }

}
