package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.person.PersonStateManager;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.LoginRequestDTO;

@RestController
@RequestMapping("/api/auths")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST})

public class AuthController {
    private final PersonStateManager personStateManager;

    AuthController( PersonStateManager personStateManager) {
        this.personStateManager = personStateManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginRequestDTO requestDTO) {
        return  personStateManager.authPerson(requestDTO);
    }
}
