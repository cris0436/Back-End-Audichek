package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AppointmentStatus implements Serializable {
    public AppointmentStatus() {}
    public AppointmentStatus( String descripcion) {
        this.descripcion = descripcion;
    }

    private Integer id;

    private String descripcion;



}