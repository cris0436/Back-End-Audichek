package ucatolica.edu.co.audichek.aplication.usecases.rolePermissions;

import org.springframework.stereotype.Component;
import ucatolica.edu.co.audichek.utils.FieldValidation;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;

@Component
public class FieldValidationRolesPermissions implements FieldValidation<String> {

    @Override
    public void validateField(String roleOrPermission) {

        if (roleOrPermission == null || roleOrPermission.isEmpty()) {
            throw new BadRequestException("this field is required");
        }
        if (roleOrPermission.length() > 30) {
            throw new BadRequestException("this field can't have more than 20 characters " + roleOrPermission);
        }


    }
}
