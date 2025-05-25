package ucatolica.edu.co.audichek.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.DoctorAvailabilityUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.GetAvailability;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.ValidateDoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.DoctorAvailabilityResponseMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorAvailabilityRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

@Configuration
public class DoctorAvailabilityUseCaseConfig {
    @Bean
    public DoctorAvailabilityUseCases doctorAvailabilityUseCases(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            MapperToEntity<DoctorAvailability, DoctorAvailabilityDTO> mapperToEntity,
            MapperToDTO<DoctorAvailability, DoctorAvailabilityDTO> mapperToDTO,
            DoctorRepository doctorRepository,
            FieldValidation<DoctorAvailability> validateDoctorAvailability,
            PersonStatusHistoryRepository personStatusHistoryRepository) {

        return new DoctorAvailabilityUseCases(
                doctorAvailabilityRepository,
                mapperToEntity,
                mapperToDTO,
                doctorRepository,
                validateDoctorAvailability,
                personStatusHistoryRepository
        );

    }
    @Bean
    public GetAvailability getAvailability(DoctorAvailabilityRepository doctorAvailabilityRepository,
                                           DoctorAvailabilityResponseMapper mapper,
                                           ValidateDoctorAvailability fieldValidation) {

        return new GetAvailability(doctorAvailabilityRepository, mapper, fieldValidation);
    }
    @Bean
    public ValidateDoctorAvailability validateDoctorAvailability() {
        return new ValidateDoctorAvailability();
    }

}
