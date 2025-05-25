package ucatolica.edu.co.audichek.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
public class RolePermission {
    public RolePermission() {}
    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }

    private Long id;

    private Role role;

    private Permission permission;
}