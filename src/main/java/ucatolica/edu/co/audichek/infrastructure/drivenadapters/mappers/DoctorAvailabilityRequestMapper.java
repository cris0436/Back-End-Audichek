package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@Component
public class DoctorAvailabilityRequestMapper implements MapperToEntity<DoctorAvailability, DoctorAvailabilityDTO>  {
    @Override
    public DoctorAvailability toEntity(DoctorAvailabilityDTO dto) {
        try {
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalTime startTime = LocalTime.parse(dto.getStartTime(), formatterTime);
            LocalTime endTime = LocalTime.parse(dto.getEndTime(), formatterTime);
            Doctor doctor = new Doctor();
            doctor.setId(UUID.fromString(dto.getDoctorId()));
            LocalDate dateAvailability =   LocalDate.parse(dto.getDateAvailability(), formatterDate);

            return new DoctorAvailability(dateAvailability,startTime,endTime,doctor);

        }
        catch (Exception e) {
            throw new ConflictException("Error al convertir el objeto DoctorAvailabilityRequestDTO a DoctorAvailability");
        }
    }

}
