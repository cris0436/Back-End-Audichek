package ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "login_records")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_record_id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;


}