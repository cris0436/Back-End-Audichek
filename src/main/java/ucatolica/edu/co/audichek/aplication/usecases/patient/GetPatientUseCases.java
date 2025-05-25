package ucatolica.edu.co.audichek.aplication.usecases.patient;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetPatientUseCases {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MapperToDTO<Patient, PatientDTO> mapperToDTO;

    @PreAuthorize("hasAuthority('VIEW_PROFILE')")
    public PatientDTO getSelfPatient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<Person> personAuth = personRepository.findPersonById(userId);
        Optional<Patient> patient = patientRepository.findPatientByPerson(personAuth.get());
        if (patient.isEmpty()) {
            throw new BadRequestException("The patient does not exist");
        }
        return mapperToDTO.toDTO(patient.get());
    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public PatientDTO getPatientById(String id) {
        try {
            UUID idUUID = UUID.fromString(id);
            Optional<Patient> patient = patientRepository.findById(idUUID);
            if (patient.isEmpty()) {
                throw new BadRequestException("id not found");
            }
            return mapperToDTO.toDTO(patient.get());
        }
        catch (Exception e) {
            throw new BadRequestException("id not valid") ;
        }

    }

    @PreAuthorize("hasAuthority('VIEW_USERS')")
    public List<PatientDTO> getPatient() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(mapperToDTO::toDTO).toList();
    }

}
