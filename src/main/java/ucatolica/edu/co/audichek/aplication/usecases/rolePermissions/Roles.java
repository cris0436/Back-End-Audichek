package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

public enum Roles {
    DOCTOR, ADMIN,PATIENT,CUSTOMIZED;

    public String toString() {
        return this.name();
    }

    public static Roles fromString(String role) {
        try {
            return Roles.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status not valuable: " + role);
        }
    }
}
