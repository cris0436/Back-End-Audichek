package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePersonRequestDTO {
    private String roleName;
    private String personId;
}
