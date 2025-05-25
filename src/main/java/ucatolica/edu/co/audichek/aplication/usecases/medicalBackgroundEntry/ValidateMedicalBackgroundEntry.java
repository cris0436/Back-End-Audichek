package ucatolica.edu.co.audichek.aplication.usecases.medicalBackgroundEntry;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalBackgroundEntry;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.utils.FieldValidation;


public class ValidateMedicalBackgroundEntry implements FieldValidation<MedicalBackgroundEntry> {

    @Override
    public void validateField(MedicalBackgroundEntry entry) {
        if (entry == null || entry.getDescription() == null || entry.getDescription().trim().isEmpty()) {
            throw new BadRequestException("La descripción no puede estar vacía");
        }
        if (entry.getDescription().length() > 500) {
            throw new BadRequestException("La descripción excede el límite de 500 caracteres");
        }
        entry.setDescription(sanitize(entry.getDescription()));

        if (entry.getPatient() == null || entry.getPatient().getId() == null) {
            throw new BadRequestException("El paciente es obligatorio");
        }
        validateUUID(entry.getPatient().getId().toString());
    }
}

