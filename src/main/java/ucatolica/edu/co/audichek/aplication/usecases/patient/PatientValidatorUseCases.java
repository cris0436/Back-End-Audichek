package ucatolica.edu.co.audichek.aplication.usecases.patient;

import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.utils.FieldValidation;

@Service
public class PatientValidatorUseCases implements FieldValidation<Patient> {

    @Override
    public void validateField (Patient patient) {
        if (patient.getPerson() == null) {
            throw new BadRequestException("person cannot be null");
        }
        if(patient.getMedicalBackground().length()  >= 500){
            throw new BadRequestException("the maximum number of medical background entries is 500");
        }
    }




}
