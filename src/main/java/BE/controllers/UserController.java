package BE.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

// Exceptions
import BE.exceptions.NotImplementedException;
// Models
import BE.entities.user.User;
// Spring
import BE.responsemodels.user.PrivilegeModel;
import BE.responsemodels.user.UserModel;
import BE.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

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
        return userService.getUserByUserName(username);
    }

    @RequestMapping(value = "/user_privileges", method = RequestMethod.GET)
    public List<PrivilegeModel> getListOfUserPrivileges() {
        return userService.getAllPrivileges();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    public UserModel getCurrentUser(Principal principal) {
        return userService.getUserByUserName(principal.getName());
    }

    @RequestMapping(value = "/current_user",params = {"action=update"}, method = RequestMethod.POST)
    public UserModel updateCurrentUser(@RequestBody UserModel user) {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}",params = {"action=create"}, method = RequestMethod.POST)
    public UserModel createUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/users/{username}",params = {"action=update"}, method = RequestMethod.POST)
    public UserModel updateUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/users/{username}",params = {"action=delete"}, method = RequestMethod.POST)
    public UserModel deleteUser(@PathVariable(value="username") String username) {
        return userService.deleteUser(username);
    }

}
