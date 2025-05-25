package ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability;

import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStates;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorAvailabilityRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



public class DoctorAvailabilityUseCases {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final MapperToEntity<DoctorAvailability, DoctorAvailabilityDTO> mapperToEntity;
    private final MapperToDTO<DoctorAvailability, DoctorAvailabilityDTO> mapperToDTO;
    private final DoctorRepository doctorRepository;
    private final FieldValidation<DoctorAvailability> validateDoctorAvailability;
    private final PersonStatusHistoryRepository personStatusHistoryRepository;

    public DoctorAvailabilityUseCases(DoctorAvailabilityRepository doctorAvailabilityRepository,
                                      MapperToEntity<DoctorAvailability, DoctorAvailabilityDTO> mapperToEntity,
                                      MapperToDTO<DoctorAvailability, DoctorAvailabilityDTO> mapperToDTO,
                                      DoctorRepository doctorRepository,
                                      FieldValidation<DoctorAvailability> validateDoctorAvailability,
                                      PersonStatusHistoryRepository personStatusHistoryRepository) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.mapperToEntity = mapperToEntity;
        this.mapperToDTO = mapperToDTO;
        this.doctorRepository = doctorRepository;
        this.validateDoctorAvailability = validateDoctorAvailability;
        this.personStatusHistoryRepository = personStatusHistoryRepository;
    }

    public List<DoctorAvailabilityDTO> getAllDoctorAvailabilities() {
        return doctorAvailabilityRepository.findAll().stream().map(mapperToDTO::toDTO).toList();
    }


    public DoctorAvailabilityDTO getDoctorAvailabilityById(String id) {
        validateDoctorAvailability.validateUUID(id);
        Optional<DoctorAvailability> doctorAvailability = doctorAvailabilityRepository.findById(UUID.fromString(id));
        if (doctorAvailability.isEmpty()) {
            throw new BadRequestException("Doctor availability with the given ID does not exist");
        }
        return mapperToDTO.toDTO(doctorAvailability.get());
    }

    @Transactional
    public DoctorAvailabilityDTO addDoctorAvailability(DoctorAvailabilityDTO doctorAvailabilityDTO) {
        validateDoctorAvailability.validateUUID(doctorAvailabilityDTO.getDoctorId());
        DoctorAvailability doctorAvailability = mapperToEntity.toEntity(doctorAvailabilityDTO);
        validateDoctorAvailability.validateField(doctorAvailability);
        Optional<Doctor> doctor = doctorRepository.findById(doctorAvailability.getDoctor().getId());
        if (doctor.isEmpty()) {
            throw new BadRequestException("Doctor does not exist with the provided ID.");
        }
        PersonStatusHistory personStatus = personStatusHistoryRepository.findPersonStatusHistoriesByPersonAndIsActive(doctor.get().getPerson(), true);
        if (personStatus == null) {
            throw new ConflictException("Doctor does not have a valid status.");
        }
        if (!personStatus.getPersonStatus().getDescripcion().equals(PersonStates.ACTIVE.toString())) {
            throw new BadRequestException("Doctor cannot be added because he is not active");
        }

        //validate time
        if (!doctorAvailability.getStartTime().isBefore(doctorAvailability.getEndTime())) {
            throw new BadRequestException("The start time must be before the end time.");
        }
        if(doctorAvailability.getDateAvailability().isBefore(LocalDate.now())){
            throw new BadRequestException("The date must be after today.");
        }
        if(!doctorAvailabilityRepository.findByDoctor_IdAndDateAvailabilityAndStartTime(
                doctorAvailability.getDoctor().getId(),
                doctorAvailability.getDateAvailability(),
                doctorAvailability.getStartTime()
        ).isEmpty()){
            throw new BadRequestException("The time is already taken.");
        }


        return mapperToDTO.toDTO(doctorAvailabilityRepository.save(doctorAvailability));
    }

    @Transactional
    public void updateAvailability(UUID id, boolean isAvailability) {
        Optional<DoctorAvailability> existingAvailability = doctorAvailabilityRepository.findById(id);
        if (existingAvailability.isPresent()) {
            DoctorAvailability availability = existingAvailability.get();
            availability.setIsAvailable(isAvailability);
            doctorAvailabilityRepository.save(availability);
            return;
        }
        throw new BadRequestException("Doctor availability with the given ID does not exist");

    }

    @Transactional
    public DoctorAvailabilityDTO updateDoctorAvailability(String id, DoctorAvailabilityDTO doctorAvailabilityDTO) {
        validateDoctorAvailability.validateUUID(id);
        DoctorAvailability updatedDoctorAvailability = mapperToEntity.toEntity(doctorAvailabilityDTO);
        validateDoctorAvailability.validateField(updatedDoctorAvailability);
        Optional<DoctorAvailability> existingAvailability = doctorAvailabilityRepository.findById(UUID.fromString(id));
        if (existingAvailability.isPresent()) {
            updatedDoctorAvailability.setId(existingAvailability.get().getId());
            PersonStatusHistory personStatus = personStatusHistoryRepository.findPersonStatusHistoriesByPersonAndIsActive(existingAvailability.get().getDoctor().getPerson(), true);
            if (personStatus == null) {
                throw new ConflictException("Doctor does not have a valid status.");
            }
            if (!personStatus.getPersonStatus().getDescripcion().equals(PersonStates.ACTIVE.toString())) {
                throw new BadRequestException("Doctor cannot be added because he is not active");
            }

            return mapperToDTO.toDTO(doctorAvailabilityRepository.save(updatedDoctorAvailability));
        }

        throw new BadRequestException("Doctor availability with the given ID does not exist");
    }

    @Transactional
    public void deleteDoctorAvailability(String id) {
        if (doctorAvailabilityRepository.existsById(UUID.fromString(id))) {
            doctorAvailabilityRepository.deleteById(UUID.fromString(id));
        }
        else{
            throw new BadRequestException("Doctor availability with the given ID does not exist");
        }
    }
}

