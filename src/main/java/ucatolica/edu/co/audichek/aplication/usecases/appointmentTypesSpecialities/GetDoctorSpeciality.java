package ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorSpecialty;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorSpecialityRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;


public class GetDoctorSpeciality {

    private DoctorSpecialityRepository  doctorSpecialityRepository;
    private FieldValidation<String> fieldValidation;

    public GetDoctorSpeciality(DoctorSpecialityRepository  doctorSpecialityRepository,
                               FieldValidation<String> fieldValidation){
        this.doctorSpecialityRepository = doctorSpecialityRepository;
        this.fieldValidation = fieldValidation;

    }

    public DoctorSpecialty getDoctorSpeciality(String speciality){
        speciality=fieldValidation.sanitizeSnakeCase(speciality);
        List<DoctorSpecialty> doctorSpeciality= doctorSpecialityRepository.findBySpecialty(speciality);
        if(doctorSpeciality.isEmpty()){
            DoctorSpecialty newDoctorSpecialty = new DoctorSpecialty();
            newDoctorSpecialty.setSpecialty(speciality);
            return doctorSpecialityRepository.save(newDoctorSpecialty);
        }
        return doctorSpeciality.getFirst();
    }
}
