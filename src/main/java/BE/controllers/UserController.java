package BE.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

// Exceptions
import BE.exceptions.InvalidRequestStructureException;
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
     * @param username username of the user to retrieve
     * @return user with requested username
     */
    @RequestMapping(value = "/users/{username}", method= RequestMethod.GET)
    public UserModel getUser(@PathVariable(value="username") String username)  {
        return userService.getUserByUserName(username);
    }

    /**
     * Gets all of the user privileges
     * @return list of user privileges
     */
    @RequestMapping(value = "/user_privileges", method = RequestMethod.GET)
    public List<PrivilegeModel> getListOfUserPrivileges() {
        return userService.getAllPrivileges();
    }

    /**
     * Gets the current user
     * @param principal
     * @return user
     */
    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    public UserModel getCurrentUser(Principal principal) {
        return userService.getUserByUserName(principal.getName());
    }

    /**
     * Updates the current user
     * @param user the user to update
     * @return current user
     */
    @RequestMapping(value = "/current_user",params = {"action="+Action.UPDATE}, method = RequestMethod.POST)
    public UserModel updateCurrentUser(@RequestBody UserModel user) {
        return userService.updateUser(user);
    }

    /**
     * Creates a new user
     * @param username the username of the user to create
     * @param user the user to create
     * @return user
     */
    @RequestMapping(value = "/users/{username}",params = {"action="+Action.CREATE}, method = RequestMethod.POST)
    public UserModel createUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        user.setUsername(username);
        return userService.createUser(user);
    }

    /**
     * Updates a specific user
     * @param username the username of the user to update
     * @param user the user to update
     * @return user
     */
    @RequestMapping(value = "/users/{username}",params = {"action="+Action.UPDATE}, method = RequestMethod.POST)
    public UserModel updateUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        return userService.updateUser(user);
    }

    /**
     * Deletes a specific user
     * @param username the username of the user to delete
     * @return user
     */
    @RequestMapping(value = "/users/{username}",params = {"action="+Action.DELETE}, method = RequestMethod.POST)
    public UserModel deleteUser(@PathVariable(value="username") String username) {
        return userService.deleteUser(username);
    }

}
