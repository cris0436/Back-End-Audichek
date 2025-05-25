package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolePermissionsRequestDTO {
    private String role;
    private List<String> permissions;
}
