package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentStatusRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;

import java.util.List;


public class AppointmentStatusUseCases {

    private final AppointmentStatusRepository appointmentStatusRepository;
    private final PatientRepository patientRepository;
    private final AppointmentStatusHistoryRepository appointmentStatusHistoryRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentStatusUseCases(
            AppointmentStatusRepository appointmentStatusRepository,
            PatientRepository patientRepository,
            AppointmentStatusHistoryRepository appointmentStatusHistoryRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.appointmentStatusRepository = appointmentStatusRepository;
        this.patientRepository = patientRepository;
        this.appointmentStatusHistoryRepository = appointmentStatusHistoryRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Create a new AppointmentStatusHistory (with default values)
    @Transactional
    public void createAppointmentStatusHistory(Appointment appointment ,String descripcion) {
        appointmentStatusHistoryRepository.save(new AppointmentStatusHistory(getAppointmentStatus(descripcion),appointment));
    }

    @Transactional
    public AppointmentStatusHistory saveAppointmentStatusHistory(AppointmentStatusHistory appointmentStatusHistory) {
        return appointmentStatusHistoryRepository.save(appointmentStatusHistory);
    }

    @Transactional
    public AppointmentStatus getAppointmentStatus(String descripcion) {
        return appointmentStatusRepository.findByDescripcion(descripcion)
                .orElseGet(() -> {
                    AppointmentStatus newAppointmentStatus = new AppointmentStatus(descripcion);
                    return appointmentStatusRepository.save(newAppointmentStatus);
                });
    }

    public List<AppointmentStatusHistory> getAppointmentHistoryByAppointmentActivate(Appointment existingAppointment) {
        return appointmentStatusHistoryRepository.findAppointmentStatusHistoriesByAppointmentAndIsActive(existingAppointment,true);
    }

    public List<Appointment> getAppointmentConfirmed(Appointment existingAppointment) {

        return appointmentRepository.findAppointmentByPatient_IdAndDoctor_IdAndDateAppointmentAndStartTime(
                existingAppointment.getPatient().getId(),
                existingAppointment.getDoctor().getId(),
                existingAppointment.getDateAppointment(),
                existingAppointment.getStartTime());
    }





}
