package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Admin;

import java.util.UUID;

public interface AdminRepository extends JpaRepository <Admin, UUID> {
}
