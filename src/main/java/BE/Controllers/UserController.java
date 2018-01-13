package BE.Controllers;

import java.util.Arrays;
import java.util.List;

import BE.Models.UserModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /**
     * Templates for testing sake TODO remove
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
        List<UserModel> list = Arrays.asList(users);
        return list;
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping("/users/{username}")
    public UserModel getUser(@PathVariable(value="username") String username) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(username)) return users[i];
        }
        return new UserModel(String.format(template, username), "content");
    }

    @RequestMapping("/user_privileges")
    public UserModel getListOfUserPrivileges(@PathVariable(value="username") String username) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(username)) return users[i];
        }
        return new UserModel(String.format(template, username), "content");
    }

}
