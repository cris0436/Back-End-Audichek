package ucatolica.edu.co.audichek.aplication.usecases.audiometry;

import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryRequestDTO;

import java.time.Instant;
import java.util.List;

import ucatolica.edu.co.audichek.utils.FieldValidation;


public class ValidateAudiometryDataUseCase implements FieldValidation<AudiometryRequestDTO> {
    @Override
    public void validateField(AudiometryRequestDTO dto) {
        if (isNullOrEmpty(dto.getAudiometryType())) {
            throw new BadRequestException("El tipo de audiometría es obligatorio o contiene caracteres inválidos.");
        }

        if (isNullOrEmpty(dto.getEvaluatedEar())) {
            throw new BadRequestException("El oído evaluado es obligatorio o contiene caracteres inválidos.");
        }

        if (validateUuid(dto.getDoctorId())) {
            throw new BadRequestException("El ID del doctor es inválido.");
        }

        if (validateUuid(dto.getPatientId())) {
            throw new BadRequestException("El ID del paciente es inválido.");
        }

        if (dto.getTestDate() != null && dto.getTestDate().isAfter(Instant.now())) {
            throw new BadRequestException("La fecha de la prueba no puede ser futura.");
        }

        if (dto.getData() == null || dto.getData().isEmpty()) {
            throw new BadRequestException("La lista de datos frecuencia-decibel no puede estar vacía.");
        }

        validateFrequencyDecibel(dto.getData());
    }

    public boolean validateUuid(String uuid) {
        if (!uuid.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            return true;
        }
        return false;
    }

    private void validateFrequencyDecibel(List<AudiometryRequestDTO.FrequencyDecibelPair> audiometryList) {
        for (AudiometryRequestDTO.FrequencyDecibelPair pair : audiometryList) {
            if (pair.getDecibelLevel() > 120 || pair.getDecibelLevel() < 0 ||
            pair.getFrequencyHz()>20000 || pair.getFrequencyHz()<100) {
                throw new BadRequestException("Invalid frecuency or decibel level");
            }
        }


    }

    private boolean isNullOrEmpty(String value) {
        String specialCharRegex = "[^a-zA-Z0-9 ]";
        if (value.matches(".*" + specialCharRegex + ".*")) {
            throw new BadRequestException("Some special characters are not allowed"+value);

        }
        return value == null || value.isEmpty();
    }

}
