package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PatientRequestDTO;

@Component
public class PatientMapper
        implements MapperToDTO< Patient,PatientDTO>  ,
        MapperToEntity< Patient,PatientRequestDTO> {
    @Override
    public PatientDTO toDTO(Patient patient) {
        if (patient == null) {
            throw new BadRequestException("El objeto Patient es nulo.");
        }

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(patient.getId());
        patientDTO.setMedicalBackground(patient.getMedicalBackground());

        if (patient.getPerson() != null) {
            patientDTO.setPersonId(patient.getPerson().getId());
            patientDTO.setName(patient.getPerson().getName());
            patientDTO.setEmail(patient.getPerson().getEmail());
            patientDTO.setBirthDate(patient.getPerson().getBirthDate());

            if (patient.getPerson().getLegalRepresentative() != null &&
                    patient.getPerson().getLegalRepresentative().getPerson() != null) {
                patientDTO.setLegalRepresentativeIdPerson(patient.getPerson().getLegalRepresentative().getPerson().getId());
                patientDTO.setLegalRepresentativeRelation(patient.getPerson().getLegalRepresentative().getRelation());
            }
        }

        return patientDTO;
    }
    @Override
    public Patient toEntity (PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        Person person = new Person();
        person.setId(patientRequestDTO.getPersonId());
        patient.setPerson(person);
        patient.setMedicalBackground(patientRequestDTO.getMedicalBackground());
        return patient;
    }


}
