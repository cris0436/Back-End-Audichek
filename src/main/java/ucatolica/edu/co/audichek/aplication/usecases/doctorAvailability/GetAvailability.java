package ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability;

import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.ManageSpecialitiesAppointmentTypes;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.DoctorAvailabilityResponseMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorAvailabilityRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;

import java.util.ArrayList;
import java.util.List;


public class GetAvailability {
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorAvailabilityResponseMapper mapper;
    private final ValidateDoctorAvailability fieldValidation;

    public GetAvailability(DoctorAvailabilityRepository doctorAvailabilityRepository,
                           DoctorAvailabilityResponseMapper mapper,
                           ValidateDoctorAvailability fieldValidation) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.mapper = mapper;
        this.fieldValidation = fieldValidation;
    }

    public List<DoctorAvailabilityDTO> getDoctorsAvailabilityByAppoitmentType(String appoitmentType){
        appoitmentType=fieldValidation.sanitizeSnakeCase(appoitmentType);
        List<String> specialties = ManageSpecialitiesAppointmentTypes.getSpecialtiesByAppointmentsTypes(appoitmentType.toUpperCase());
        List<DoctorAvailabilityDTO> doctorAvailabilityList= new ArrayList<DoctorAvailabilityDTO>();
        for (String specialty : specialties) {
            doctorAvailabilityList.addAll(getDoctorsAvailability(specialty));
        }
        return doctorAvailabilityList;

    }

    public List<DoctorAvailabilityDTO> getDoctorsAvailability(String specialty){
        List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityRepository.findByDoctor_Specialty_SpecialtyAndIsAvailable(specialty.toUpperCase(),true);
        return doctorAvailabilityList.stream().map(mapper::toDTO).toList();
    }
}
