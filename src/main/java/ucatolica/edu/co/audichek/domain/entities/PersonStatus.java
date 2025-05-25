package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonStatus {

    public PersonStatus() {}
    public PersonStatus( String descripcion) {
        this.descripcion = descripcion;
    }
    private Integer id;

    private String descripcion;
}