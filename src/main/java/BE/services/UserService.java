package BE.services;

import BE.responsemodels.user.PrivilegeModel;
import BE.responsemodels.user.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<UserModel> getAllUsers();

    public UserModel getUserByUserName(String username);

    public UserModel createUser(UserModel user);

    public UserModel updateUser(UserModel user);

    public UserModel deleteUser(String username);

    public List<PrivilegeModel> getAllPrivileges();
}
