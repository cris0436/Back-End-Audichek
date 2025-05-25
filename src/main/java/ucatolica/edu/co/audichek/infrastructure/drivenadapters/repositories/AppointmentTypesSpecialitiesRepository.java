package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentTypesSpecialities;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorSpecialty;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentTypesSpecialitiesRepository extends JpaRepository<AppointmentTypesSpecialities, UUID> {
    List<AppointmentTypesSpecialities> findByAppointmentType_DescriptionAndSpecialties_Specialty(String appointmentTypeDescription, String specialtiesSpecialty);

    List<AppointmentTypesSpecialities> findByAppointmentType_DescriptionAndSpecialties(String appointmentTypeDescription, DoctorSpecialty specialties);
}
