package ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDate;


public class ValidateDoctorAvailability implements FieldValidation<DoctorAvailability> {

    @Override
    public void validateField(DoctorAvailability doctorAvailability) {
        if (doctorAvailability.getDoctor()==null || doctorAvailability.getDateAvailability()==null ||doctorAvailability.getStartTime()==null ||doctorAvailability.getEndTime()==null) {
            throw new ConflictException("doctor cannot be null");
        }
        validateTime(doctorAvailability);
        validateDate(doctorAvailability);
    }

    public void validateDate(DoctorAvailability doctorAvailability) {
        if (doctorAvailability == null || doctorAvailability.getDateAvailability() == null) {
            throw new ConflictException("Date availability cannot be null");
        }
        LocalDate today = LocalDate.now();
        if (doctorAvailability.getDateAvailability().isBefore(today)) {
            throw new ConflictException("Date availability cannot be in the past");
        }
    }

    public void validateTime(DoctorAvailability doctorAvailability) {
        if (doctorAvailability.getStartTime() == null || doctorAvailability.getEndTime() == null ||
                doctorAvailability.getStartTime().isAfter(doctorAvailability.getEndTime()) ||
                doctorAvailability.getStartTime().equals(doctorAvailability.getEndTime()) ) {

            throw new ConflictException("Invalid time range");
        }
    }


}
