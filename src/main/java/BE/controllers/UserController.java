package BE.controllers;

import java.util.List;

// Exceptions
import BE.exceptions.NotImplementedException;
// Models
import BE.models.user.UserModel;
// Spring
import BE.models.user.UserPrivilegesModel;
import BE.repositories.UserRepository;
import BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Gets all users
     * @return a list of all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping(value = "/users/{username}", method= RequestMethod.GET)
    public UserModel getUser(@PathVariable(value="username") String username)  {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/user_privileges", method = RequestMethod.GET)
    public List<UserPrivilegesModel> getListOfUserPrivileges() {
        // TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    public UserModel getCurrentUser() {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.PATCH)
    public UserModel updateCurrentUser(@RequestBody UserModel user) {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public UserModel createUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        throw new NotImplementedException();

    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.PATCH)
    public UserModel updateUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        // TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public UserModel deleteUser(@PathVariable(value="username") String username) {
        // TODO this
        throw new NotImplementedException();
    }

}
