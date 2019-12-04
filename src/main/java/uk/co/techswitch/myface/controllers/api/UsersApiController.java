package uk.co.techswitch.myface.controllers.api;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.users.CreateUser;
import uk.co.techswitch.myface.models.api.users.UpdateUser;
import uk.co.techswitch.myface.models.api.users.UserModel;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.UsersService;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private final UsersService usersService;


    public UsersApiController(UsersService usersService) {
        this.usersService = usersService;
    }


    @RequestMapping(value = "/{id}", method = GET)
    public UserModel getUserById(@PathVariable("id") long id){
        User user = usersService.getById(id);

        return new UserModel(user);
    }

    @RequestMapping(value = "", method = GET)
    public ResultsPage searchUsers(UsersFilter filter){
        List<User> users = usersService.searchUsers(filter);
        int numberOfUsersFound = usersService.countUsers(filter);

        ResultsPage results = new ResultsPageBuilder<UserModel,UsersFilter>()
                .withItems(users.stream().map(UserModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberOfUsersFound)
                .withBaseUrl("/api/users")
                .build();

        return results;
    }


    @RequestMapping(value = "/create", method = POST)
    public UserModel createUser(@ModelAttribute @Valid CreateUser createUser){
        User user = usersService.createUser(createUser);

        return new UserModel(user);
    }


    @RequestMapping(value = "/{id}", method = POST)
    public UserModel updateUser(@PathVariable("id") long id, @ModelAttribute @Valid UpdateUser updateUser) {
        User user = usersService.updateUser(id, updateUser);

        return new UserModel(user);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteUser(@PathVariable("id") long id){
        usersService.deleteUser(id);
    }



}
