package ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorSpecialty;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.PersonDTO;

@Component
public class DoctorMapper implements MapperToEntity<Doctor, DoctorRequestDTO> , MapperToDTO<Doctor, DoctorResponseDTO> {
    @Autowired
    MapperToDTO<Person, PersonDTO> personMapper;

    @Override
    public Doctor toEntity(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = new Doctor();
        Person person = new Person();
        DoctorSpecialty doctorSpecialty = new DoctorSpecialty();
        doctorSpecialty.setSpecialty(doctorRequestDTO.getSpecialty());
        person.setId(doctorRequestDTO.getPersonId());
        doctor.setPerson(person);
        doctor.setSpecialty(doctorSpecialty);
        doctor.setProfessionalsLicense(doctorRequestDTO.getProfessionalsLicense());
        return doctor;
    }

    @Override
    public DoctorResponseDTO toDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setDoctorId(doctor.getId());
        dto.setSpecialty(doctor.getSpecialty().getSpecialty());
        dto.setPerson(personMapper.toDTO(doctor.getPerson()));
        dto.setProfessionalsLicense(doctor.getProfessionalsLicense());
        return dto;
    }
}
