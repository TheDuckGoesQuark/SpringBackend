package BE.services;

import BE.models.user.UserModel;

import java.util.List;

public interface UserService {
    public List<UserModel> getAllUsers();

    public UserModel getUserByUserName(String username);

    public UserModel createUser(UserModel user);

    public UserModel updateUser(UserModel user);

    UserModel deleteUser(String username);
}
