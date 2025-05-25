package ucatolica.edu.co.audichek.aplication.usecases.audiometry.strategy;

import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AudiometryRequestDTO;

public interface AudiometryCreationStrategy {
    void execute(AudiometryRequestDTO request);
}
