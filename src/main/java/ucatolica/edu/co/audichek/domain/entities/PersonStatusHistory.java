package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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

    private Integer id;

    private Person person; // Relación unidireccional a `Person`

    private PersonStatus personStatus;

    private LocalDateTime changeDate;

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