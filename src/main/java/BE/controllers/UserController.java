package BE.controllers;

import java.util.List;

// Exceptions
import BE.exceptions.NotImplementedException;
import BE.exceptions.UserNotFoundException;
// Models
import BE.exceptions.UsernameAlreadyExistsException;
import BE.models.user.User;
import BE.models.user.UserModel;
// Spring
import BE.models.user.UserPrivilegesModel;
import BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserRepository repository;


    /**
     * Gets all users
     * @return a list of all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    /**
     * Gets a user based on username
     * @param username username of user to retrieve
     * @return user with requested username
     */
    @RequestMapping(value = "/users/{username}", method= RequestMethod.GET)
    public User getUser(@PathVariable(value="username") String username)  {
        User user = repository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        else return user;

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
    public User createUser(@PathVariable(value="username") String username, @RequestBody UserModel user) {
        if (repository.findByUsername(username) != null) throw new UsernameAlreadyExistsException();
        else {
            User newuser = new User(username);
            repository.save(newuser);
            return newuser;
        }
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
