package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Doctor implements Serializable {

    private UUID id;  // El id de Doctor es igual al id de Person


    private DoctorSpecialty specialty;

    private Person person;

    private String professionalsLicense;
}