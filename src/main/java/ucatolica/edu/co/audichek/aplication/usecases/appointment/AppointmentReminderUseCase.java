package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentReminderUseCase {
    private final AppointmentRepository appointmentRepository;
    public AppointmentReminderUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Scheduled(cron = "0 0 8 * * ?") // Se ejecuta todos los días a las 8 AM
    public void sendReminders() {
        LocalDate now = LocalDate.now();
        LocalDate reminderTime = now.plusDays(1); // Buscar citas dentro de 24h

        List<Appointment> appointments = appointmentRepository.findAppointmentByDateAppointment(reminderTime);

        for (Appointment appointment : appointments) {
            String recipient = appointment.getDoctor().getPerson().getEmail(); // O número de teléfono
            System.out.println("se envia un mensaje a :" +recipient);
            String message = "Hola, recuerda tu cita mañana a las " + appointment.getDateAppointment();

        }
    }
}
