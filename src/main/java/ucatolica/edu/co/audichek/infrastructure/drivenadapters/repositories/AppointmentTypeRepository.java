package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentType;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, UUID> {
    List<AppointmentType> findByDescription(String description);
}
