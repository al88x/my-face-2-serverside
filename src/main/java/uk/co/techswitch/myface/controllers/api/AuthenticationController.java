package uk.co.techswitch.myface.controllers.api;


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
    private final UsersService usersService;


    public AuthenticationController(AuthenticationService authenticationService, UsersService usersService) {
        this.authenticationService = authenticationService;
        this.usersService = usersService;
    }

    @PostMapping(value = "/signup")
    public void createPassword(@ModelAttribute @Valid AuthenticationCredentials authenticationCredentials) {
        List<User> users = usersService.searchUsers(new UsersFilter(authenticationCredentials.getUserName()));
        authenticationService.createPassword(users.get(0).getId(), authenticationCredentials);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity checkPassword(@ModelAttribute @Valid AuthenticationCredentials credentials){
        return authenticationService.checkPassword(credentials);
    }
}
