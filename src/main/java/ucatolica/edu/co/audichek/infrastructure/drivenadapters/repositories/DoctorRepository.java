package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findDoctorByPerson(Person person);

    Optional<Doctor> findDoctorByPerson_Id(String personId);

    Optional<Doctor> findDoctorById(UUID id);

    Optional<Doctor> findDoctorByProfessionalsLicense(String professionalsLicense);
}
