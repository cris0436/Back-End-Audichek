package ucatolica.edu.co.audichek.aplication.usecases.appointment.Observer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentLogDTO;

import java.util.List;


public class AppointmentSubject {
    private final List<AppointmentObserver> observers;

    public AppointmentSubject(List<AppointmentObserver> observers) {
        this.observers = observers;
    }

    public void notifyObservers(AppointmentLogDTO appointmentLogDTO) {
        for (AppointmentObserver observer : observers) {
            observer.update(appointmentLogDTO);
        }
    }
}
