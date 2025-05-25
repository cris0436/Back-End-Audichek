package ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities;

import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentType;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentTypesSpecialities;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentTypeRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentTypesSpecialitiesRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.util.List;


public class GetAppointmentType {
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final FieldValidation<String> fieldValidation;
    private final GetDoctorSpeciality getDoctorSpeciality;
    private final AppointmentTypesSpecialitiesRepository  appointmentTypesSpecialitiesRepository;

    public GetAppointmentType(AppointmentTypeRepository  appointmentTypeRepository,
                              FieldValidation<String> fieldValidation,
                              GetDoctorSpeciality getDoctorSpeciality,
                              AppointmentTypesSpecialitiesRepository  appointmentTypesSpecialitiesRepository){
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.fieldValidation = fieldValidation;
        this.getDoctorSpeciality = getDoctorSpeciality;
        this.appointmentTypesSpecialitiesRepository = appointmentTypesSpecialitiesRepository;

    }


    public void validateSpecialityAppointmentType(String appointmentType){
        appointmentType=fieldValidation.sanitizeSnakeCase(appointmentType);
        List<String> specialities = ManageSpecialitiesAppointmentTypes.getSpecialtiesByAppointmentsTypes(appointmentType);
        for (String speciality : specialities) {
            List<AppointmentTypesSpecialities> app = appointmentTypesSpecialitiesRepository.findByAppointmentType_DescriptionAndSpecialties(
                    appointmentType,getDoctorSpeciality.getDoctorSpeciality(speciality));
            if (app.isEmpty()) {
                AppointmentTypesSpecialities newAppointmentTypesSpecialities = new AppointmentTypesSpecialities();
                newAppointmentTypesSpecialities.setAppointmentType(getAppointmentType(appointmentType));
                newAppointmentTypesSpecialities.setSpecialties(getDoctorSpeciality.getDoctorSpeciality(speciality));
                appointmentTypesSpecialitiesRepository.save(newAppointmentTypesSpecialities);
            }
        }

    }

    public AppointmentType getAppointmentType(String appointmentType){
        appointmentType=fieldValidation.sanitizeSnakeCase(appointmentType);
        List<AppointmentType> appointmentTypeList= appointmentTypeRepository.findByDescription(appointmentType);
        if(appointmentTypeList.isEmpty()){
            AppointmentType newAppointmentType = new AppointmentType();
            newAppointmentType.setDescription(appointmentType);
            return appointmentTypeRepository.save(newAppointmentType);
        }
        return appointmentTypeList.getFirst();
    }
}
