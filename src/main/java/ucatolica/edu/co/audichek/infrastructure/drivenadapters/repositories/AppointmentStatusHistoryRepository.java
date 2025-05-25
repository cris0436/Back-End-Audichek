package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;

import java.util.List;

@Repository
public interface AppointmentStatusHistoryRepository extends JpaRepository<AppointmentStatusHistory, Integer> {

    @Query("""
            select a from AppointmentStatusHistory a
            where a.appointment.doctor = ?1 and a.isActive = ?2 and a.appointmentStatus.descripcion = ?3""")
    List<AppointmentStatusHistory> findByAppointment_DoctorAndIsActiveAndAppointmentStatus_Descripcion(Doctor appointmentDoctor, Boolean isActive, String appointmentStatusDescripcion);

    List<AppointmentStatusHistory> findAppointmentStatusHistoriesByAppointment(Appointment appointment);

    List<AppointmentStatusHistory> findAppointmentStatusHistoriesByAppointmentAndIsActive(Appointment appointment, Boolean isActive);


}
