

package ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentAuditLog;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentAuditLogRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;


public class AuditLogObserver implements AppointmentObserver {
    private final MapperToEntity<AppointmentAuditLog, AppointmentLogDTO> mapperToEntity;
    private final AppointmentAuditLogRepository auditRepository;

    public AuditLogObserver(AppointmentAuditLogRepository auditRepository,
                            MapperToEntity<AppointmentAuditLog, AppointmentLogDTO> mapperToEntity) {
        this.auditRepository = auditRepository;
        this.mapperToEntity = mapperToEntity;
    }

    @Override
    public void update(AppointmentLogDTO appointmentLogDTO) {
        auditRepository.save(mapperToEntity.toEntity(appointmentLogDTO));
    }
}
