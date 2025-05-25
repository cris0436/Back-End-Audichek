package ucatolica.edu.co.audichek.aplication.usecases.audiometry;

import lombok.RequiredArgsConstructor;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Audiometry;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AudiometryData;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AudiometryDataRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AudiometryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryResponseDTO;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
public class FetchAudiometryUseCase {

    private final AudiometryRepository audiometryRepository;
    private final AudiometryDataRepository dataRepository;

    public AudiometryResponseDTO execute(String audiometryId) {

        UUID audiometryIdUuid = new UUID(0, 0);
        try {
            audiometryIdUuid = UUID.fromString(audiometryId);
        } catch (Exception e) {
            throw new BadRequestException("UUID invalido");
        }

        Audiometry audiometry = audiometryRepository.findById(audiometryIdUuid)
                .orElseThrow(() -> new NotFoundException("Audiometría no encontrada"));

        List<AudiometryData> dataList = dataRepository.findByAudiometryId(audiometryIdUuid);

        // Convertir a DTO de respuesta
        List<AudiometryResponseDTO.FrequencyDecibelPair> pairList = dataList.stream().map(data -> {
            AudiometryResponseDTO.FrequencyDecibelPair pair = new AudiometryResponseDTO.FrequencyDecibelPair();
            pair.setFrequencyHz(data.getFrequency().getFrequencyHz());
            pair.setDecibelLevel(data.getDecibels().getDecibelLevel());
            return pair;
        }).toList();

        return new AudiometryResponseDTO(
                audiometry.getId(),
                audiometry.getDoctor().getId(),
                audiometry.getPatient().getId(),
                audiometry.getTestDate(),
                audiometry.getEvaluatedEar(),
                audiometry.getAudiometryType().getDescription(),
                pairList
        );
    }
}
