package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.AppointmentStatusUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.appointment.CancelAppointmentUseCases;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatus;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class DeactivatePerson {

    private final PersonStatusHistoryRepository personStatusHistoryRepository;
    private final PersonStatusRepository personStatusRepository;
    private final AppointmentStatusHistoryRepository appointmentStatusHistoryRepository;
    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final CancelAppointmentUseCases cancelAppointmentUseCases;

    @Autowired
    public DeactivatePerson(PersonStatusHistoryRepository personStatusHistoryRepository,
                            PersonStatusRepository personStatusRepository,
                            AppointmentStatusHistoryRepository appointmentStatusHistoryRepository,
                            AppointmentStatusUseCases appointmentStatusUseCases,
                            CancelAppointmentUseCases cancelAppointmentUseCases) {
        this.personStatusHistoryRepository = personStatusHistoryRepository;
        this.personStatusRepository = personStatusRepository;
        this.appointmentStatusHistoryRepository = appointmentStatusHistoryRepository;
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.cancelAppointmentUseCases = cancelAppointmentUseCases;
    }


    @Transactional
    public String deactivatePerson(Person existingPerson) {

        // Obtener historial de estado activo
        Optional<PersonStatusHistory> activeStatus = personStatusHistoryRepository
                .findPersonStatusByPersonAndIsActive(existingPerson, true)
                .stream().findFirst();

        if (activeStatus.isEmpty()) {
            throw new ConflictException("Person status history does not exist");
        }

        // Inactivar estado
        activeStatus.get().setIsActive(false);
        personStatusHistoryRepository.save(activeStatus.get());

        // Obtener estado INACTIVE o crearlo si no existe
        PersonStatus inactivePersonStatus = personStatusRepository
                .findByDescripcion(PersonStates.INACTIVE.toString())
                .orElseGet(() -> personStatusRepository.save(new PersonStatus(PersonStates.INACTIVE.toString())));

        if (existingPerson.getDoctor() != null) {
            List<AppointmentStatusHistory> appointments = appointmentStatusHistoryRepository.findByAppointment_DoctorAndIsActiveAndAppointmentStatus_Descripcion(existingPerson.getDoctor(),true,"ACTIVE");
            for (AppointmentStatusHistory appointmentStatusHistory : appointments) {
                cancelAppointmentUseCases.appointmentCancel(appointmentStatusHistory.getAppointment().getId().toString());
            }
        }
        personStatusHistoryRepository.save(new PersonStatusHistory(inactivePersonStatus, existingPerson));

        return "Person successfully deactivated.";
    }
}

