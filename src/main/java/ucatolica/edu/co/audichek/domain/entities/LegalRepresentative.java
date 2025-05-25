package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Getter
@Setter
public class LegalRepresentative implements Serializable {
    public LegalRepresentative(){}
    public LegalRepresentative(String relation, Person person) {
        this.relation = relation;
        this.person = person;
    }

    private Integer id;

    private String relation;

    private Person person;
}