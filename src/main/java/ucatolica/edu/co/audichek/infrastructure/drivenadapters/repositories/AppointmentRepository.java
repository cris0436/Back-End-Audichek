
package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDateAppointmentAndEndTimeAndStartTime(LocalDate dateAppointment, LocalTime endTime, LocalTime startTime);

    @Query(value = "SELECT h.* FROM appointment_status_history h " +
            "INNER JOIN appointments a ON h.appointment_id = a.appointment_id " +
            "INNER JOIN appointment_statuses s ON h.appointment_status_id = s.appointment_status_id " +
            "WHERE a.appointment_id = :appointmentId " +
            "AND s.descripcion = :statusDesc " +
            "AND h.is_active = true",
            nativeQuery = true)
    List<AppointmentStatusHistory> findPendingByAppointmentId(
            @Param("appointmentId") Integer appointmentId,
            @Param("statusDesc") String statusDescription);

    List<Appointment> findByDateAppointmentAndEndTimeAndStartTimeAndPatient_Id(LocalDate dateAppointment, LocalTime endTime, LocalTime startTime, UUID patientId);

    Optional<Appointment> findAppointmentById(UUID id);

    List<Appointment> findAppointmentByPatient_IdAndDoctor_IdAndDateAppointmentAndStartTime(UUID patientId,UUID doctorId, LocalDate dateAppointment, LocalTime startTime);

    List<Appointment> findAppointmentByDateAppointment(LocalDate dateAppointment);
}
