package ucatolica.edu.co.audichek.domain.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DoctorStatus implements Serializable {
    private Integer id;

    private String descripcion;

}