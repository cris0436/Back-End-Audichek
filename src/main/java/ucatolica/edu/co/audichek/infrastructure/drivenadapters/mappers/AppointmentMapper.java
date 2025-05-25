package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentType;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Component
public class AppointmentMapper implements MapperToDTO<Appointment, AppointmentResponseDTO> ,MapperToEntity<Appointment, AppointmentRequestDTO> {
    @Autowired
    private FieldValidation<String> fieldValidation;
    @Override
    public AppointmentResponseDTO toDTO(Appointment appointment) {
        if(appointment == null) {
            return null;
        }
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setDescription(appointment.getDescription());
        dto.setDateAvailability(appointment.getDateAppointment());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        return dto;
    }

    @Override
    public Appointment toEntity(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setId(UUID.fromString(dto.getDoctorId()));
        Patient patient = new Patient();
        patient.setId(UUID.fromString(dto.getPatientId()));
        AppointmentType appointmentType = new AppointmentType();

        appointmentType.setDescription(fieldValidation.sanitizeSnakeCase(dto.getType()));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateAppointment(LocalDate.parse(dto.getDateAvailability()));
        appointment.setEndTime(LocalTime.parse(dto.getEndTime()));
        appointment.setStartTime(LocalTime.parse(dto.getStartTime()));
        appointment.setDescription(dto.getDescription());
        appointment.setAppointmentType(appointmentType);
        return appointment;
    }
}
