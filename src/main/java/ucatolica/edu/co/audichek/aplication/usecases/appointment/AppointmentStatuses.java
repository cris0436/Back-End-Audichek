package ucatolica.edu.co.audichek.aplication.usecases.appointment;

public enum AppointmentStatuses {
    PENDING,
    CONFIRMED,
    CANCELLED,
    REPROGRAMMED;
    public String toString() {
        return this.name();
    }

    public static AppointmentStatuses fromString(String status) {
        try {
            return AppointmentStatuses.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status not valuable: " + status);
        }
    }
}
