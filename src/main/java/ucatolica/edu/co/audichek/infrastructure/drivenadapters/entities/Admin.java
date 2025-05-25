package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "admins")
public class Admin implements Serializable {
    public Admin(){}

    public Admin(Person person, String notes){
        this.person = person;
        this.registrationDate = LocalDate.now();
        this.notes = notes;
    }

    @Id
    @GeneratedValue
    @Column(name = "admin_id", nullable = false)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person person;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "notes", nullable = true, length = 200)
    private String notes;
}
