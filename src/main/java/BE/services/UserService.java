package BE.services;

import BE.entities.user.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUserByUserName(String username);

    public User createUser(User user);

    public User updateUser(User user);

    User deleteUser(String username);
}
