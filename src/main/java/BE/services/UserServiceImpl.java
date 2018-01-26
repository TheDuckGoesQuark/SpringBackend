package BE.services;

import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.exceptions.UserNotFoundException;
import BE.exceptions.UserAlreadyExistsException;
import BE.repositories.PrivilegeRepository;
import BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        else return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) throw new UserAlreadyExistsException();
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) throw new UserNotFoundException();
        // .save performs both update and creation
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User deleteUser(String username) {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException();
        return userRepository.deleteByUsername(username);
    }

    @Override
    public List<Privilege> getAllPrivileges() {
        return (List<Privilege>) privilegeRepository.findAll();
    }
}
