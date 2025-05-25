package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Admin;
import ucatolica.edu.co.audichek.aplication.usecases.person.FirstAdminService;

@RestController
@RequestMapping("/first_admins")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.POST})
public class FirstAdminController {

    private final FirstAdminService firstAdminService;

    public FirstAdminController(@Autowired FirstAdminService firstAdminService) {
        this.firstAdminService = firstAdminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String firstAdmin(@RequestBody Admin admin) {
        return firstAdminService.createFirsAdmin(admin);
    }

}
