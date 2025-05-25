package ucatolica.edu.co.audichek.domain.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class LoginLog {
    private Integer id;

    private Instant date;

    private Person person;


}