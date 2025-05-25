package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.implementations;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AppointmentRepositoryImp  implements AppointmentRepository {
    @Override
    public List<Appointment> findByDateAppointmentAndEndTimeAndStartTime(LocalDate dateAppointment, LocalTime endTime, LocalTime startTime) {
        return List.of();
    }

    @Override
    public List<AppointmentStatusHistory> findPendingByAppointmentId(Integer appointmentId, String statusDescription) {
        return List.of();
    }

    @Override
    public List<Appointment> findByDateAppointmentAndEndTimeAndStartTimeAndPatient_Id(LocalDate dateAppointment, LocalTime endTime, LocalTime startTime, UUID patientId) {
        return List.of();
    }

    @Override
    public Optional<Appointment> findAppointmentById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Appointment> findAppointmentByPatient_IdAndDoctor_IdAndDateAppointmentAndStartTime(UUID patientId, UUID doctorId, LocalDate dateAppointment, LocalTime startTime) {
        return List.of();
    }

    @Override
    public List<Appointment> findAppointmentByDateAppointment(LocalDate dateAppointment) {
        return List.of();
    }
}
