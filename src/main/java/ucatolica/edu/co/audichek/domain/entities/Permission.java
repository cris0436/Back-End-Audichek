package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {
    private Integer id;

    private String permissionName;
}