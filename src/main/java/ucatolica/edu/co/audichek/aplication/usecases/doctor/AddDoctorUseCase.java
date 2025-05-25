package ucatolica.edu.co.audichek.aplication.usecases.doctor;

import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.GetDoctorSpeciality;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStates;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonValidationUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.Roles;
import ucatolica.edu.co.audichek.exceptions.ConflictException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorSpecialty;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorResponseDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class AddDoctorUseCase {

    private final DoctorRepository doctorRepository;
    private final FieldValidation<Doctor> validateDoctor;
    private final PersonValidationUseCases validatePerson;
    private final MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO;
    private final MapperToEntity<Doctor, DoctorRequestDTO> mapperToEntity;
    private final PersonRepository personRepository;
    private final PersonStatusHistoryRepository personStatusHistoryRepository;
    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;
    private final GetDoctorSpeciality getDoctorSpeciality;

    public AddDoctorUseCase(DoctorRepository doctorRepository,
                            FieldValidation<Doctor> validateDoctor,
                            PersonValidationUseCases validatePerson,
                            MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO,
                            MapperToEntity<Doctor, DoctorRequestDTO> mapperToEntity,
                            PersonRepository personRepository,
                            PersonStatusHistoryRepository personStatusHistoryRepository,
                            ManageRolesPermissionsUseCases manageRolesPermissionsUseCases,
                            GetDoctorSpeciality getDoctorSpeciality) {
        this.doctorRepository = doctorRepository;
        this.validateDoctor = validateDoctor;
        this.validatePerson = validatePerson;
        this.mapperToDTO = mapperToDTO;
        this.mapperToEntity = mapperToEntity;
        this.personRepository = personRepository;
        this.personStatusHistoryRepository = personStatusHistoryRepository;
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
        this.getDoctorSpeciality = getDoctorSpeciality;
    }

    public DoctorResponseDTO addDoctor (DoctorRequestDTO doctorRequestDTO){
        doctorRequestDTO.setPersonId(validatePerson.sanitize(doctorRequestDTO.getPersonId()));
        doctorRequestDTO.setSpecialty(validatePerson.sanitizeSnakeCase(doctorRequestDTO.getSpecialty()));

        validatePerson.verifyID(doctorRequestDTO.getPersonId());

        validateDoctor.validateField(mapperToEntity.toEntity(doctorRequestDTO));
        Optional<Person> person = personRepository.findById(doctorRequestDTO.getPersonId());
        if (person.isEmpty()) {
            throw new BadRequestException("Person does not exist");
        }
        if (doctorRepository.findDoctorByPerson(person.get()).isPresent()){
            throw new BadRequestException("The doctor already exists");
        }
        DoctorSpecialty doctorSpecialty= getDoctorSpeciality.getDoctorSpeciality(doctorRequestDTO.getSpecialty());

        Doctor doctor = mapperToEntity.toEntity(doctorRequestDTO);
        doctorRepository.findDoctorByProfessionalsLicense(
                doctorRequestDTO.getProfessionalsLicense()).orElseThrow(()-> new ConflictException("Doctor whit that professionals license already exists"));

        doctor.setPerson(person.get());
        doctor.setSpecialty(doctorSpecialty);
        //check status person
        List<PersonStatusHistory> personStatusHistoryList = personStatusHistoryRepository.
                findPersonStatusByPersonAndIsActive(person.get(), true);
        if (personStatusHistoryList.size() != 1) {
            throw new BadRequestException("person status history already do not exist");
        }
        if (!Objects.equals(personStatusHistoryList.getFirst().getPersonStatus().getDescripcion(), PersonStates.PENDING.toString())){
            manageRolesPermissionsUseCases.addRolePermissionHistory(Roles.DOCTOR.toString(), person.get().getId());
        }

        return mapperToDTO.toDTO(doctorRepository.save(doctor));
    }

}
