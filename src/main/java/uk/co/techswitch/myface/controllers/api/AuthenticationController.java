package uk.co.techswitch.myface.controllers.api;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.passwords.AuthenticationCredentials;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.AuthenticationService;
import uk.co.techswitch.myface.services.UsersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signup")
    public void createPassword(@RequestHeader("Authorization") String credentials) {
        authenticationService.createPassword(credentials);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity checkPassword(@RequestHeader("Authorization") String credentials){
        return authenticationService.checkPassword(credentials);
    }
}
