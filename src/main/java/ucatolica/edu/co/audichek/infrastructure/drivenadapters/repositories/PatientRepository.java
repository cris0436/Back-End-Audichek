package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findPatientByPerson(Person person);

    Optional<Patient> getPatientById(UUID id);
}
