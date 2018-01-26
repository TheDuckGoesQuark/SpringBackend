package BE.controllers;

import java.util.List;

// Exceptions
import BE.entities.user.Privilege;
import BE.exceptions.NotImplementedException;
// Models
import BE.exceptions.UserNotFoundException;
import BE.entities.user.User;
// Spring
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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping(value = "/users/{username}", method= RequestMethod.GET)
    public User getUser(@PathVariable(value="username") String username)  {
        return userService.getUserByUserName(username);
    }

    @RequestMapping(value = "/user_privileges", method = RequestMethod.GET)
    public List<Privilege> getListOfUserPrivileges() {
        // TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    public User getCurrentUser() {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.PATCH)
    public User updateCurrentUser(@RequestBody User user) {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public User createUser(@PathVariable(value="username") String username, @RequestBody User user) {
        throw new NotImplementedException();

    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.PATCH)
    public User updateUser(@PathVariable(value="username") String username, @RequestBody User user) {
        // TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable(value="username") String username) {
        // TODO this
        throw new NotImplementedException();
    }

}
