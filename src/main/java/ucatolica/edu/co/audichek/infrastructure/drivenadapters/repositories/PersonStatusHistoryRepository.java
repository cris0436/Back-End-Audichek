package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.repository.CrudRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;

import java.util.List;

public interface PersonStatusHistoryRepository extends CrudRepository<PersonStatusHistory, Integer> {

    List<PersonStatusHistory> getPersonStatusHistoriesByPersonAndIsActive(Person person, Boolean isActive);

    List<PersonStatusHistory> findPersonStatusHistoriesByPersonAndPersonStatusAndIsActive(Person person, PersonStatus personStatus, Boolean isActive);

    List<PersonStatusHistory> findPersonStatusByPersonAndIsActive(Person person, Boolean isActive);

    PersonStatusHistory findPersonStatusHistoriesByPersonAndIsActive(Person person, Boolean isActive);
}
