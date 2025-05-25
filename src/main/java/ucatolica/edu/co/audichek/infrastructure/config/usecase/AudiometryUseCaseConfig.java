package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.FetchAudiometryUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.ValidateAudiometryDataUseCase;
import ucatolica.edu.co.audichek.aplication.usecases.audiometry.strategy.DefaultAudiometryCreationStrategy;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AudiometryMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;

@Configuration
public class AudiometryUseCaseConfig {
    @Bean
    public FetchAudiometryUseCase fetchAudiometryUseCase(AudiometryRepository audiometryRepository,
                                                         AudiometryDataRepository dataRepository) {
        return new FetchAudiometryUseCase(audiometryRepository, dataRepository);
    }
    @Bean
    public ValidateAudiometryDataUseCase validateAudiometryDataUseCase(){
        return new ValidateAudiometryDataUseCase();
    }
    @Bean
    public DefaultAudiometryCreationStrategy defaultAudiometryCreationStrategy(
            AudiometryRepository audiometryRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            AudiometryFrequencyRepository frequencyRepository,
            AudiometryDecibelRepository decibelRepository,
            AudiometryDataRepository dataRepository,
            AudiometryTypeRepository audiometryTypeRepository,
            AudiometryMapper audiometryMapper) {

        return new DefaultAudiometryCreationStrategy(
                audiometryRepository,
                doctorRepository,
                patientRepository,
                frequencyRepository,
                decibelRepository,
                dataRepository,
                audiometryTypeRepository,
                audiometryMapper
        );
    }






}
