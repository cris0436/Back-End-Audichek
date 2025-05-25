package ucatolica.edu.co.audichek.aplication.usecases.doctor;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.regex.Pattern;


public class ValidateDoctor implements FieldValidation<Doctor> {
    private static final Pattern RETHUS_PATTERN = Pattern.compile("^(MP|RTH|COL)-(\\d{4})?-?\\d+$");

    @Override
    public void validateField(Doctor doctor) {

        if (doctor == null || doctor.getPerson() == null || doctor.getSpecialty() == null || doctor.getSpecialty().equals("")) {
            throw new IllegalArgumentException("this doctor is null");
        }
        registerValidate(doctor.getProfessionalsLicense());
    }


    public void registerValidate(String professionalRegister) {
        if (professionalRegister == null || professionalRegister.isEmpty()) {
            throw new BadRequestException("Register cannot be null or empty.");
        }
        if (!RETHUS_PATTERN.matcher(professionalRegister).matches()) {
            throw new BadRequestException(String.format("Register bad format: %s. Format should be MP-XXXX-XXXX, RTH-XXXX-XXXX, or COL-XXXX-XXXX.", professionalRegister));
        }
    }
}
