package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Permissions {
    // 🔐 Perfil personal
    VIEW_PROFILE,
    EDIT_PROFILE,
    CHANGE_PASSWORD,

    // 👥 Gestión de usuarios (Admin)
    CREATE_USER,
    EDIT_USER,
    DELETE_USER,
    VIEW_USERS,
    ASSIGN_ROLE,

    // 🩺 Gestión médica
    VIEW_DOCTORS,
    ASSIGN_DOCTOR, // Admin asigna doctor a paciente
    VIEW_MEDICAL_HISTORY,
    EDIT_MEDICAL_HISTORY, // Solo el doctor edita historial

    // 🗓️ Citas médicas
    CREATE_APPOINTMENT,
    VIEW_APPOINTMENTS,
    CANCEL_APPOINTMENT,
    MODIFY_APPOINTMENT,
    VIEW_DOCTOR_AVAILABILITY,

    // 👨‍⚕️ Funciones del doctor
    VIEW_ASSIGNED_PATIENTS,
    MANAGE_DIAGNOSES,
    REGISTER_TREATMENT,

    // 📄 Representantes legales
    VIEW_REPRESENTED_DATA,
    MANAGE_REPRESENTED_DATA,

    // ⚙️ Administración del sistema
    ADD_DOCTOR,
    MANAGE_PERMISSIONS,
    VIEW_LOGS,
    CONFIRMED_PERSON,
    SYSTEM_CONFIGURATION;

    // 📌 Permisos por rol

    public static List<String> doctorPermissions() {
        return Stream.of(
                VIEW_PROFILE,
                EDIT_PROFILE,
                CHANGE_PASSWORD,
                VIEW_ASSIGNED_PATIENTS,
                MANAGE_DIAGNOSES,
                REGISTER_TREATMENT,
                VIEW_APPOINTMENTS
        ).map(Enum::name).collect(Collectors.toList());
    }

    public static List<String> patientPermissions() {
        return Stream.of(
                VIEW_PROFILE,
                EDIT_PROFILE,
                CHANGE_PASSWORD,
                VIEW_DOCTORS,
                CREATE_APPOINTMENT,
                VIEW_APPOINTMENTS,
                CANCEL_APPOINTMENT,
                MODIFY_APPOINTMENT,
                VIEW_DOCTOR_AVAILABILITY,
                VIEW_MEDICAL_HISTORY
        ).map(Enum::name).collect(Collectors.toList());
    }

    public static List<String> representativePermissions() {
        return Stream.of(
                VIEW_PROFILE,
                EDIT_PROFILE,
                CHANGE_PASSWORD,
                VIEW_REPRESENTED_DATA,
                MANAGE_REPRESENTED_DATA
        ).map(Enum::name).collect(Collectors.toList());
    }

    public static List<String> adminPermissions() {
        return Stream.of(
                VIEW_PROFILE,
                EDIT_PROFILE,
                CHANGE_PASSWORD,
                CREATE_USER,
                EDIT_USER,
                DELETE_USER,
                VIEW_USERS,
                ASSIGN_ROLE,
                ADD_DOCTOR,
                MANAGE_PERMISSIONS,
                VIEW_LOGS,
                SYSTEM_CONFIGURATION,
                ASSIGN_DOCTOR,
                CONFIRMED_PERSON
        ).map(Enum::name).collect(Collectors.toList());
    }
    public  static List<String> rolePermissions(Roles role) {
        switch (role) {
            case DOCTOR:
                return doctorPermissions();
            case PATIENT:
                return patientPermissions();
            case ADMIN:
                return adminPermissions();
            default:
                return List.of();
        }
    }

    public static List<String> allPermissions() {
        return Stream.of(values()).map(Enum::name).collect(Collectors.toList());
    }

    public String toString() {
        return this.name();
    }


    public static List<String> noPermissions() {
        return List.of();
    }

    public static Permissions fromString(String permission) {
        try {
            return Permissions.valueOf(permission.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Permission not valid: " + permission);
        }
    }
}
