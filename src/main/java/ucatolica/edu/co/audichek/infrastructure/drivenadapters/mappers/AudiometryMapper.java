package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Audiometry;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryType;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryRequestDTO;

@Component
public class AudiometryMapper  {

    public Audiometry toAudiometryEntity(AudiometryRequestDTO dto, Doctor doctor, Patient patient, AudiometryType audiometryTypeDescription) {
        Audiometry audiometry = new Audiometry();
        audiometry.setDoctor(doctor);
        audiometry.setPatient(patient);
        audiometry.setTestDate(dto.getTestDate());
        audiometry.setEarScore(0); // calculable después
        audiometry.setEvaluatedEar(dto.getEvaluatedEar());
        audiometry.setAudiometryType(audiometryTypeDescription);
        return audiometry;
    }
}