package ucatolica.edu.co.audichek.aplication.usecases.audiometry.strategy;

import lombok.RequiredArgsConstructor;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.*;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AudiometryMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryRequestDTO;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
public class DefaultAudiometryCreationStrategy implements AudiometryCreationStrategy {

    private final AudiometryRepository audiometryRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AudiometryFrequencyRepository frequencyRepository;
    private final AudiometryDecibelRepository decibelRepository;
    private final AudiometryDataRepository dataRepository;
    private final AudiometryTypeRepository audiometryTypeRepository;
    private final AudiometryMapper audiometryMapper;

    @Override
    public void execute(AudiometryRequestDTO request) {
        // Buscar doctor y paciente
        UUID doctorId = new UUID(0, 0);
        UUID patientId = new UUID(0, 0);
        try {
            doctorId = UUID.fromString(request.getDoctorId());
            patientId = UUID.fromString(request.getPatientId());
        } catch (Exception e) {
            throw new BadRequestException("UUID invalido");
        }
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BadRequestException("Doctor no encontrado"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new BadRequestException("Paciente no encontrado"));

        // Buscar o crear tipo de audiometría
        AudiometryType audiometryType = audiometryTypeRepository.findAll().stream()
                .filter(type -> type.getDescription().equalsIgnoreCase(request.getAudiometryType()))
                .findFirst()
                .orElseGet(() -> {
                    AudiometryType newType = new AudiometryType();
                    newType.setDescription(request.getAudiometryType());
                    return audiometryTypeRepository.save(newType);
                });

        // Utilizar el mapper para convertir el DTO en entidad
        Audiometry audiometry = audiometryMapper.toAudiometryEntity(
                request, doctor, patient, audiometryType
        );
        audiometry = audiometryRepository.save(audiometry); // guardar audiometría principal

        // Crear registros de datos
        for (var pair : request.getData()) {
            // Buscar o crear frecuencia
            AudiometryFrequency frequency = frequencyRepository
                    .findByFrequencyHz(pair.getFrequencyHz())
                    .orElseGet(() -> {
                        AudiometryFrequency newFreq = new AudiometryFrequency();
                        newFreq.setFrequencyHz(pair.getFrequencyHz());
                        return frequencyRepository.save(newFreq);
                    });

            // Buscar o crear decibel
            AudiometryDecibel decibel = decibelRepository
                    .findByDecibelLevel(pair.getDecibelLevel())
                    .orElseGet(() -> {
                        AudiometryDecibel newDecibel = new AudiometryDecibel();
                        newDecibel.setDecibelLevel(pair.getDecibelLevel());
                        return decibelRepository.save(newDecibel);
                    });

            // Crear registro de datos asociados
            AudiometryData data = new AudiometryData();
            data.setAudiometry(audiometry);
            data.setFrequency(frequency);
            data.setDecibels(decibel);
            data.setTestDate(LocalDateTime.now());
            data.setAudiometryType(audiometryType);
            dataRepository.save(data);
        }
    }
}