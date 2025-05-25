package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentAuditLog;

import java.util.UUID;

public interface AppointmentAuditLogRepository extends JpaRepository<AppointmentAuditLog, UUID> {
}
