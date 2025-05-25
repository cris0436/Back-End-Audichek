package ucatolica.edu.co.audichek.utils;

import ucatolica.edu.co.audichek.exceptions.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public interface FieldValidation<T> {
    void validateField(T object);

    default String sanitize(String field){
        return  field.replaceAll("[^a-zA-Z0-9]", "");
    };

    default String sanitizeSnakeCase(String field) {
        return field.replaceAll("[^a-zA-Z0-9_]", "").toUpperCase();
    }

    default void validateUUID(String uuid) {
        if (!uuid.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
           throw new BadRequestException("the UUID is invalid");
        }

    }

    default LocalTime validateLocalTime(String timeString) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(timeString, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Bad format to time: " + timeString);
        }
    }

    default LocalDate validateLocalDate(String dateString) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Bad format to date : " + dateString);
        }
    }

}
