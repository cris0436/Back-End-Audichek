package ucatolica.edu.co.audichek.aplication.usecases.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Person;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PersonRepository;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PersonValidationUseCases implements FieldValidation<Person> {

    @Autowired
    private PersonRepository personRepository;

    public boolean validatePerson(String personId) {
        return personRepository.findById(personId).isPresent();
    }

    public boolean validateUnderAge(Person person) {
        LocalDate birthDate = person.getBirthDate();
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            validateTutor(person);
        }
        return age < 18;
    }

    @Override
    public void validateField(Person person) {
        if (isNullOrEmpty(person.getId()) ||
                isNullOrEmpty(person.getName()) ||
                isNullOrEmpty(person.getEmail()) ||
                isNullOrEmpty(person.getPassword()) ||
                person.getPassword().length() < 8 ||
                person.getBirthDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Some fields are missing or invalid");
        }

        validateEmail(person.getEmail());
        verifyID(person.getId());
        verifySpecialCharacters(person.getName());
    }
    private void validateTutor(Person person) {
        if (person.getLegalRepresentative() == null) {
            throw new BadRequestException("Person does not have a legal representative");
        }
    }

    private void verifySpecialCharacters(String text) {
        String specialCharRegex = "[^a-zA-Z0-9 ]";
        if (text.matches(".*" + specialCharRegex + ".*")) {
            throw new BadRequestException("Some special characters are not allowed");
        }
    }

    public void verifyID(String id) {
        if (id.length() < 8 || !id.matches("\\d+")) {
            throw new BadRequestException("The ID is not valid");
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new BadRequestException("Invalid email");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }


}
