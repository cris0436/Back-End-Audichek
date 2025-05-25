package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
public class RolePersonHistory {

    public RolePersonHistory(){}

    public RolePersonHistory(Role role, Person person) {
        this.role = role;
        this.person = person;
        this.isActive = true;
        this.date = LocalDateTime.now();
    }
    private Integer id;

    private Role role;


    private Person person;


    private Boolean isActive;

    private LocalDateTime date;
}