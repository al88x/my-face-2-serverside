package uk.co.techswitch.myface.controllers.api;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.users.UpdateUser;
import uk.co.techswitch.myface.models.api.users.UserModel;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.UsersService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private final UsersService usersService;

    public UsersApiController(UsersService usersService) {
        this.usersService = usersService;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public UserModel updateUser(@PathVariable("id") long id, @ModelAttribute @Valid UpdateUser updateUser) {
        User user = usersService.updateUser(id, updateUser);

        return new UserModel(user);
    }

}
