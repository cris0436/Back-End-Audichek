package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @Column(name = "person_id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "`password`", nullable = false, length = 300)
    private String password;

    // Relación uno a uno con Doctor, con la columna doctor_id en la tabla persons
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL ,optional = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctor;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,optional = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,optional = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Admin admin;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "legal_representative_id", nullable = true)
    private LegalRepresentative legalRepresentative;






}
