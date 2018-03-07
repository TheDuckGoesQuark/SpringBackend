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

import javax.servlet.http.HttpServletResponse;

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
    public List<UserModel> getAllUsers(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.getAllUsers();
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping(value = "/users/{username}", method= RequestMethod.GET)
    public UserModel getUser(@PathVariable(value="username") String username, HttpServletResponse response)  {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.getUserByUserName(username);
    }

    @RequestMapping(value = "/user_privileges", method = RequestMethod.GET)
    public List<PrivilegeModel> getListOfUserPrivileges(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.getAllPrivileges();
    }

    @RequestMapping(value = "/current_user", method = RequestMethod.GET)
    public UserModel getCurrentUser(Principal principal, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.getUserByUserName(principal.getName());
    }

    @RequestMapping(value = "/current_user",params = {"action="+Action.UPDATE}, method = RequestMethod.POST)
    public UserModel updateCurrentUser(@RequestBody UserModel user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/users/{username}",params = {"action="+Action.CREATE}, method = RequestMethod.POST)
    public UserModel createUser(@PathVariable(value="username") String username, @RequestBody UserModel user, HttpServletResponse response) {
        user.setUsername(username);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return userService.createUser(user);
    }

    @RequestMapping(value = "/users/{username}",params = {"action="+Action.UPDATE}, method = RequestMethod.POST)
    public UserModel updateUser(@PathVariable(value="username") String username, @RequestBody UserModel user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/users/{username}",params = {"action="+Action.DELETE}, method = RequestMethod.POST)
    public UserModel deleteUser(@PathVariable(value="username") String username, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return userService.deleteUser(username);
    }

}
