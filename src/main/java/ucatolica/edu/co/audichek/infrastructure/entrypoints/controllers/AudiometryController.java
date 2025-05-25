package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.FetchAudiometryUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.strategy.AudiometryCreationStrategy;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.ValidateAudiometryDataUseCase;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AudiometryMapper;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryResponseDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/audiometries")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST, RequestMethod.GET})
@RequiredArgsConstructor
public class AudiometryController {

    private final ValidateAudiometryDataUseCase validateUseCase;
    private final AudiometryCreationStrategy creationStrategy;
    private final FetchAudiometryUseCase fetchAudiometryUseCase;

    @PostMapping
    public ResponseEntity<Void> createAudiometry(@RequestBody AudiometryRequestDTO request) {
        validateUseCase.validateField(request);
        creationStrategy.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AudiometryResponseDTO> getAudiometry(@PathVariable String id){
        AudiometryResponseDTO response = fetchAudiometryUseCase.execute(id);
        return ResponseEntity.ok(response);

    }

}
