package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "role_permissions_history")
public class RolePersonHistory {

    public RolePersonHistory(){}

    public RolePersonHistory(Role role, Person person) {
        this.role = role;
        this.person = person;
        this.isActive = true;
        this.date = LocalDateTime.now();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_permissions_id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne( optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role", nullable = false ,referencedColumnName = "role_id")
    private Role role;


    @ManyToOne(optional = false)
    @JoinColumn(name = "person", nullable = false ,referencedColumnName = "person_id")
    private Person person;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
