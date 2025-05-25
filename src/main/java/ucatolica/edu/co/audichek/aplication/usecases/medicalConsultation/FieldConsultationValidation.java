package ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation;

import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.MedicalConsultationDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FieldConsultationValidation implements FieldValidation <MedicalConsultationDTO> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public LocalDateTime convertStringToLocalDateTimeAndBack(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            return dateTime;

        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format: " + dateStr);
        }
    }

    @Override
    public void validateField(MedicalConsultationDTO medicalConsultationDTO) {
        if (medicalConsultationDTO.getDoctorId() == null || medicalConsultationDTO.getDoctorId().isEmpty()
        || medicalConsultationDTO.getPatientId() == null
        || medicalConsultationDTO.getPatientId().isEmpty()
        || medicalConsultationDTO.getConsultationDate() == null
        || medicalConsultationDTO.getConsultationDate().isEmpty()
        || medicalConsultationDTO.getInstructions() == null
        || medicalConsultationDTO.getInstructions().isEmpty()) {
            throw new IllegalArgumentException("this field is required");
        }
    }

}
