package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.medicalBackgroundEntry.MedicalBackgroundService;
import ucatolica.edu.co.audichek.aplication.usecases.medicalBackgroundEntry.ValidateMedicalBackgroundEntry;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalBackgroundEntryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;

@Configuration
public class MedicalBackgroundEntryConfig {
    @Bean
    public MedicalBackgroundService medicalBackgroundService(
            MedicalBackgroundEntryRepository entryRepository,
            PatientRepository patientRepository,
            ValidateMedicalBackgroundEntry validateMedicalBackgroundEntry) {
    return new MedicalBackgroundService(entryRepository, patientRepository, validateMedicalBackgroundEntry);
    }

    @Bean
    public ValidateMedicalBackgroundEntry validateMedicalBackgroundEntry() {
        return new ValidateMedicalBackgroundEntry();
    }




}
