package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Admin implements Serializable {
    public Admin(){}

    public Admin(Person person, String notes){
        this.person = person;
        this.registrationDate = LocalDate.now();
        this.notes = notes;
    }

    private UUID id;

    private Person person;

    private LocalDate registrationDate;

    private String notes;
}