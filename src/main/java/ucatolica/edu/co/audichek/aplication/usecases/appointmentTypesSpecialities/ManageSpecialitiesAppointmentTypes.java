
package ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class ManageSpecialitiesAppointmentTypes {


    public static EnumMap<Specialities, List<AppointmentTypes>> getAppointmentsBySpecialty() {
        EnumMap<Specialities, List<AppointmentTypes>> appointments = new EnumMap<>(Specialities.class);
        appointments.put(Specialities.AUDIOMETRA, Arrays.asList(AppointmentTypes.CONSULTA_AUDIOMETRICA, AppointmentTypes.PRUEBA_AUDIOMETRIA, AppointmentTypes.EXAMEN_IMPEDANCIOMETRIA));
        appointments.put(Specialities.SONOMETRA, Arrays.asList(AppointmentTypes.PRUEBA_AUDIOMETRIA, AppointmentTypes.EXAMEN_IMPEDANCIOMETRIA));
        appointments.put(Specialities.OTORRINOLARINGOLOGIA, Arrays.asList(AppointmentTypes.CONSULTA_OTORRINO, AppointmentTypes.ADAPTACION_PROTESIS_AUDITIVA, AppointmentTypes.TERAPIA_AUDITIVA));
        appointments.put(Specialities.MEDICINA_GENERAL, Arrays.asList(AppointmentTypes.CONTROL_MEDICO_GENERAL, AppointmentTypes.CONSULTA_OTORRINO));
        return appointments;
    }

    public static List<String> getAppointmentsTypesBySpecialty(String specialty) {
        isValidSpecialty(specialty);
        EnumMap<Specialities, List<AppointmentTypes>>  appo  =getAppointmentsBySpecialty();
        for (Specialities key : appo.keySet()) {
            if (key.name().equals(specialty.toUpperCase())) {
                return appo.get(key).stream().map(Enum::name).toList();
            }
        }
        throw new BadRequestException("Specialty not valid: " + specialty );
    }

    public static void isValidSpecialty(String specialty) {
        try {
            Specialities.valueOf(specialty.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Specialty not valid: " + specialty +"\n"
            +"choose another of that specialty : " +"\n"+ getAppointmentsTypesBySpecialty(specialty) +"\n");
        }
    }

    public static boolean isValidTypeAppointment(String typeAppointment) {
        try {
            AppointmentTypes.valueOf(typeAppointment.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static List<String> getSpecialtiesByAppointmentsTypes(String appointmentTypes) {
        if (!isValidTypeAppointment(appointmentTypes)) {
            throw new BadRequestException("Type appointment not valid: " + appointmentTypes );
        }

        AppointmentTypes type = AppointmentTypes.valueOf(appointmentTypes.toUpperCase());
        EnumMap<Specialities, List<AppointmentTypes>> appointments = getAppointmentsBySpecialty();

        return appointments.entrySet().stream()
                .filter(entry -> entry.getValue().contains(type))
                .map(entry -> entry.getKey().name())
                .toList();
    }

}
