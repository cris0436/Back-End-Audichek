package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentAuditLog;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogResponseDTO;

@Component
public class AppointmentLogMapper implements MapperToDTO<AppointmentAuditLog, AppointmentLogResponseDTO>,
MapperToEntity<AppointmentAuditLog, AppointmentLogDTO>{
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Override
    public AppointmentLogResponseDTO toDTO(AppointmentAuditLog entity) {
        AppointmentLogResponseDTO dto = new AppointmentLogResponseDTO();
        dto.setAction( entity.getAction());
        dto.setNewAppointment(appointmentMapper.toDTO( entity.getNewAppointment()));
        dto.setPreviousAppointment( appointmentMapper.toDTO(entity.getPreviousAppointment()));
        dto.setTimestamp( entity.getTimestamp());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    @Override
    public AppointmentAuditLog toEntity(AppointmentLogDTO dto) {

        return new AppointmentAuditLog(
                dto.getAction(),dto.getUserId(),dto.getPreviousAppointment(),dto.getNewAppointment()
        );
    }
}
