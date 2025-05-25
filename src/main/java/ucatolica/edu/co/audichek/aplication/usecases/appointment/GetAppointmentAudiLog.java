package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentAuditLog;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentAuditLogRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogResponseDTO;

import java.util.List;


public class GetAppointmentAudiLog {
    private final AppointmentAuditLogRepository appointmentAuditLogRepository;
    private final MapperToDTO<AppointmentAuditLog, AppointmentLogResponseDTO> mapperToDTO;

    public GetAppointmentAudiLog(AppointmentAuditLogRepository appointmentAuditLogRepository,
                                 MapperToDTO<AppointmentAuditLog, AppointmentLogResponseDTO> mapperToDTO) {
        this.appointmentAuditLogRepository = appointmentAuditLogRepository;
        this.mapperToDTO = mapperToDTO;
    }

    public List<AppointmentLogResponseDTO> getAppointmentAudiLog(){
        return appointmentAuditLogRepository.findAll().stream().map(mapperToDTO::toDTO).toList();
    }
}
