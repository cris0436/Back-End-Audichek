package ucatolica.edu.co.audichek.aplication.usecases.doctor;

import ucatolica.edu.co.audichek.aplication.usecases.person.PersonValidationUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MapperToDTO;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorResponseDTO;

import java.util.List;
import java.util.Optional;


public class GetDoctorUseCase {
    private final DoctorRepository doctorRepository;
    private final PersonValidationUseCases personValidation;
    private final MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO;

    public GetDoctorUseCase(DoctorRepository doctorRepository,
                            PersonValidationUseCases personValidation,
                            MapperToDTO<Doctor, DoctorResponseDTO> mapperToDTO) {
        this.doctorRepository = doctorRepository;
        this.personValidation = personValidation;
        this.mapperToDTO = mapperToDTO;
    }

    public DoctorResponseDTO getDoctor(String personId){
        personValidation.verifyID(personId);
        Optional<Doctor> doctor = doctorRepository.findDoctorByPerson_Id(personId);
        if (doctor.isEmpty()){
            throw new BadRequestException("Doctor does not exist");
        }
        return mapperToDTO.toDTO(doctor.get());
    }

    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorRepository.findAll().stream().map(mapperToDTO::toDTO).toList();
    }
}
