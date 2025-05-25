package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import org.springframework.security.access.prepost.PreAuthorize;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class GetAppointmentUseCases {

    private final AppointmentRepository appointmentRepository;
    private final MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO;
    private final AppointmentValidationUseCases appointmentValidationUseCases;

    public GetAppointmentUseCases(AppointmentRepository appointmentRepository,
                                  MapperToDTO<Appointment, AppointmentResponseDTO> mapperToDTO,
                                  AppointmentValidationUseCases appointmentValidationUseCases) {
        this.appointmentRepository = appointmentRepository;
        this.mapperToDTO = mapperToDTO;
        this.appointmentValidationUseCases = appointmentValidationUseCases;
    }

    @PreAuthorize("hasAuthority('VIEW_ASSIGNED_PATIENTS')")
    public List<AppointmentResponseDTO> getAllAppointments (){
        return appointmentRepository.findAll().stream()
                .map(mapperToDTO::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDTO getAppointmentById(String appintmentId){
        appointmentValidationUseCases.validateUUID(appintmentId);
        return appointmentRepository.findById(UUID.fromString(appintmentId))
                .map(mapperToDTO::toDTO)
                .orElse(null);

    }



}
