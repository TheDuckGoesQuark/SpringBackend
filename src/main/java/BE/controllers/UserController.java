package BE.controllers;

import java.util.Arrays;
import java.util.List;

// Exceptions
import BE.exceptions.NotImplementedException;
import BE.exceptions.UserNotFoundException;
// Models
import BE.models.user.UserModel;
// Spring
import BE.models.UserPrivilegesModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    /**
     * Templates for testing sake TODO remove and replace with DB connection and user service
     */
    private static final UserModel[] users = {
            new UserModel("JohnSmith", null, null, null, null, null, null, null),
            new UserModel("SomeGuy", null, null, null, null, null, null, null),
    };


    /**
     * Gets all users
     * @return a list of all users
     */
    @RequestMapping("/users")
    public List<UserModel> getAllUsers() {
        return Arrays.asList(users);
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping("/users/{username}")
    public UserModel getUser(@PathVariable(value="username") String username)  {
        for (UserModel user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        throw new UserNotFoundException();
    }

    @RequestMapping("/user_privileges")
    public List<UserPrivilegesModel> getListOfUserPrivileges() {
        // TODO this
        throw new NotImplementedException();
    }

    @RequestMapping("/current_user")
    public UserModel getCurrentUser() {
        //TODO this
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public UserModel createUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        // TODO this
        throw new NotImplementedException();
    }

}
