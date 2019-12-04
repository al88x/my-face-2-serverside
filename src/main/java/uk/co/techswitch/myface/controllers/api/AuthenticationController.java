package uk.co.techswitch.myface.controllers.api;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.techswitch.myface.models.api.passwords.CreateAuthentication;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.AuthenticationService;
import uk.co.techswitch.myface.services.UsersService;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
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
    public void createPassword(@ModelAttribute @Valid CreateAuthentication createAuthentication) {
        List<User> users = usersService.searchUsers(new UsersFilter(createAuthentication.getUserName()));


        authenticationService.createPassword(users.get(0).getId(), createAuthentication);
    }
}
