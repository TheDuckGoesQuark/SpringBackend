package BE.controllers;

import java.util.Arrays;
import java.util.List;

import BE.exceptions.NotImplementedException;
import BE.models.UserModel;
import BE.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /**
     * Templates for testing sake TODO remove and replace with DB connection
     */
    private static final String template = "Hello, %s!";
    private static final UserModel[] users = {
            new UserModel("JohnSmith", "Says hi"),
            new UserModel("SomeGuy", "Doesn't say hi")
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
    public UserModel getUser(@PathVariable(value="username") String username) throws UserNotFoundException {
        for (UserModel user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        throw new UserNotFoundException();
    }

    @RequestMapping("/user_privileges")
    public UserModel getListOfUserPrivileges() throws NotImplementedException {
        // TODO this
        throw new NotImplementedException();
    }

}
