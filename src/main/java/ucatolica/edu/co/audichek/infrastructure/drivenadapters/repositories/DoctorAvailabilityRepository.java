package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, UUID> {
    Optional<DoctorAvailability> findByDateAvailabilityAndStartTimeAndEndTimeAndDoctorAndIsAvailable(
            LocalDate dateAvailability,
            LocalTime startTime,
            LocalTime endTime,
            Doctor doctor,
            boolean isAvailable
    );



    Optional<DoctorAvailability> findDoctorAvailabilitiesById(UUID id);

    List<DoctorAvailability> findByDoctor_Specialty_SpecialtyAndIsAvailable(String doctorSpecialtySpecialty, Boolean isAvailable);

    List<DoctorAvailability> findByDoctor_IdAndDateAvailabilityAndStartTime(UUID doctorId, LocalDate dateAvailability, LocalTime startTime);
}
