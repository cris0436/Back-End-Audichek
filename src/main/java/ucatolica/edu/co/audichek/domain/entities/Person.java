package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Person implements Serializable {

    private String id;

    private String name;

    private String email;

    private String password;

    // Relación uno a uno con Doctor, con la columna doctor_id en la tabla persons
    private Doctor doctor;

    private Patient patient;

    private Admin admin;

    private LocalDate birthDate;

    private LegalRepresentative legalRepresentative;






}