package BE.services;

import BE.responsemodels.user.PrivilegeModel;
import BE.responsemodels.user.UserModel;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface UserService {
    public List<UserModel> getAllUsers();

    public UserModel getUserByUserName(String username);

    public UserModel createUser(UserModel user);

    public UserModel updateUser(UserModel user);

    public UserModel deleteUser(String username);

    public List<PrivilegeModel> getAllPrivileges();

    public Boolean userExists(String username);
}
