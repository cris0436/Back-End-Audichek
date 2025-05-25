package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorSpecialty;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpecialty, UUID> {
    List<DoctorSpecialty> findBySpecialty(String specialty);
}
