package BE.services;

import BE.models.user.UserModel;
import BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserModel> getAllUsers() {
        return (List<UserModel>) userRepository.findAll();
    }

    @Override
    public UserModel getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserModel createUser(UserModel user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public UserModel updateUser(UserModel user) {
        // .save performs both update and creation
        userRepository.save(user);
        return user;
    }

    @Override
    public UserModel deleteUser(String username) {
        return userRepository.deleteByUsername(username);
    }
}
