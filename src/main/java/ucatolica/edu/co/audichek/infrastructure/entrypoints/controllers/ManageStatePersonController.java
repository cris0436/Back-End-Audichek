package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStateManager;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStates;
import ucatolica.edu.co.audichek.aplication.usecases.person.state.PersonStateStrategy;

@Controller
@RestController
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST})
@RequestMapping("/api/manage_state_persons")
public class ManageStatePersonController {

    private final PersonStateManager personStateManager;

    @Autowired
    ManageStatePersonController( PersonStateManager personStateManager) {
        this.personStateManager = personStateManager;
    }
    @PostMapping("/deactivate_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String deactivateUser(@RequestParam String userId) {
        return personStateManager.deactivatePerson(userId);
    }

    @PostMapping("/activate_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String activateUser(@RequestParam String userId) {
        return personStateManager.activatePerson(userId);
    }


}
