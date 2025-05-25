package ucatolica.edu.co.audichek.aplication.usecases.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStates;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.ManageRolesPermissionsUseCases;
import ucatolica.edu.co.audichek.aplication.usecases.rolePermissions.Roles;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.PersonStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToEntity;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonStatusHistoryRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
public class AddPatientUseCases {

    private final PatientRepository patientRepository;
    private final PatientValidatorUseCases patientValidatorUseCases;
    private final PersonRepository personRepository;
    private final MapperToDTO< Patient,PatientDTO> mapperToDTO;
    private final MapperToEntity<Patient, PatientRequestDTO> mapperToEntity;
    private final PersonStatusHistoryRepository personStatusHistoryRepository;
    private final ManageRolesPermissionsUseCases manageRolesPermissionsUseCases;

    @Autowired
    public AddPatientUseCases(
            PatientRepository patientRepository,
            PatientValidatorUseCases patientValidatorUseCases,
            PersonRepository personRepository,
            MapperToDTO< Patient,PatientDTO> mapperToDTO,
            MapperToEntity<Patient, PatientRequestDTO> mapperToEntity,
            PersonStatusHistoryRepository personStatusHistoryRepository,
            ManageRolesPermissionsUseCases manageRolesPermissionsUseCases) {
        this.patientRepository = patientRepository;
        this.patientValidatorUseCases = patientValidatorUseCases;
        this.personRepository = personRepository;
        this.mapperToDTO = mapperToDTO;
        this.mapperToEntity = mapperToEntity;
        this.personStatusHistoryRepository = personStatusHistoryRepository;
        this.manageRolesPermissionsUseCases = manageRolesPermissionsUseCases;
    }

    @Transactional
    public PatientDTO addPatient(PatientRequestDTO patientDTO) {
        Patient patient = mapperToEntity.toEntity(patientDTO);
        patientValidatorUseCases.validateField(patient);
        patient.setMedicalBackground(patientValidatorUseCases.sanitize(patient.getMedicalBackground()));
        patient.getPerson().setId(patientValidatorUseCases.sanitize(patient.getPerson().getId()));
        Optional<Person> person= personRepository.getPersonById(patient.getPerson().getId());
        if (person.isEmpty()) {
            throw new NotFoundException("Person not found");
        }
        Optional<Patient> existingPatient= patientRepository.findPatientByPerson(person.get());
        if (existingPatient.isPresent()) {
            throw new BadRequestException("The patient already exists");
        }
        patient.setPerson(person.get());

        //check status person
        List<PersonStatusHistory> personStatusHistoryList = personStatusHistoryRepository.
                findPersonStatusByPersonAndIsActive(person.get(), true);
        if (personStatusHistoryList.size() != 1) {
            throw new BadRequestException("person status history already do not exist");
        }
        if (personStatusHistoryList.getFirst().getPersonStatus().getDescripcion()!= PersonStates.PENDING.toString()){
            manageRolesPermissionsUseCases.addRolePermissionHistory(Roles.PATIENT.toString(),person.get().getId());
        }
        return mapperToDTO.toDTO(patientRepository.save(patient));
    }
}
