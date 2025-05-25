package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;

import java.util.Optional;

public interface PersonStatusRepository extends JpaRepository<PersonStatus, Integer> {
    Optional<PersonStatus> findByDescripcion(String descripcion);

}
