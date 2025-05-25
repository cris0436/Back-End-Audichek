package ucatolica.edu.co.audichek.aplication.usecases.person;


public enum PersonStates {
    ACTIVE,
    PENDING,
    BLOCKED,
    INACTIVE;


    public String toString() {
        return this.name();
    }

    public static PersonStates fromString(String status) {
        try {
            return PersonStates.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status not valuable: " + status);
        }
    }
}
