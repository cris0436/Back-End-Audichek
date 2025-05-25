package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "person_status_history")
public class PersonStatusHistory {

    // Constructor por defecto
    public PersonStatusHistory() {}

    // Constructor con parámetros para inicializar los atributos importantes
    public PersonStatusHistory(PersonStatus personStatus, Person person) {
        this.personStatus = personStatus;
        this.person = person;
        this.changeDate = LocalDateTime.now();
        this.isActive = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_status_history_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person", nullable = false, referencedColumnName = "person_id")
    private Person person; // Relación unidireccional a `Person`

    @ManyToOne
    @JoinColumn(name = "person_status", nullable = false, referencedColumnName = "person_status_id")
    private PersonStatus personStatus;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Override
    public String toString() {
        return "PersonStatusHistory{" +
                "id=" + id +
                ", person=" + (person != null ? person.getId() : null) +
                ", personStatus=" + (personStatus != null ? personStatus.getId() : null) +
                ", changeDate=" + changeDate +
                ", isActive=" + isActive +
                '}';
    }
}
