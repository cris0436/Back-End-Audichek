package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PersonDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;

@Component
public class PersonMapperToDTO implements MapperToDTO<Person, PersonDTO> {

    @Override
    public PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setBirthDate(person.getBirthDate());

        // Check if the person has an associated patient
        if (person.getPatient() != null) {
            dto.setPatientId(person.getPatient().getId());
        } else {
            // Handle the case where no patient is associated
            dto.setPatientId(null); // Or whatever makes sense in your logic
        }

        // Check if the person has an associated doctor
        if (person.getDoctor() != null) {
            dto.setDoctorId(person.getDoctor().getId());
        } else {
            // Handle the case where no doctor is associated
            dto.setDoctorId(null); // Or whatever makes sense in your logic
        }

        // Check if the person has an associated legal representative
        if (person.getLegalRepresentative() != null && person.getLegalRepresentative().getPerson() != null) {
            dto.setLegalRepresentativePersonId(person.getLegalRepresentative().getPerson().getId());
        } else {
            // Handle the case where no legal representative is associated
            dto.setLegalRepresentativePersonId(null); // Or whatever makes sense in your logic
        }

        return dto;
    }

}
