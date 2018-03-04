package BE.services;

import BE.entities.UserProject;
import BE.entities.project.Project;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.exceptions.UserNotFoundException;
import BE.exceptions.UserAlreadyExistsException;
import BE.repositories.PrivilegeRepository;
import BE.repositories.UserRepository;
import BE.responsemodels.project.ProjectModel;
import BE.responsemodels.project.UserListModel;
import BE.responsemodels.user.PrivilegeModel;
import BE.responsemodels.user.ProjectListModel;
import BE.responsemodels.user.UserModel;
import BE.security.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import BE.security.passwordHash.PasswordHash;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final
    UserRepository userRepository;

    private final
    PrivilegeRepository privilegeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
    }

    // Conversion Functions
    private static UserModel userToUserModel(User user) {
        PasswordHash passwordHasher = new PasswordHash();
        String hashedPassword = passwordHasher.hashPassword(user.getPassword());
        return new UserModel(
                user.getUsername(),
                hashedPassword,
                user.getEmail(),
                // convert user projects to project list
                user.getUserProjects().stream().map(
                        UserServiceImpl::userProjectToProjectListModel
                ).collect(Collectors.toList()),
                // convert privileges to list of names as string
                user.getPrivileges().stream().map(
                        Privilege::getName
                ).collect(Collectors.toList())
        );
    }

    private static ProjectListModel userProjectToProjectListModel(UserProject userProject) {
        Project project = userProject.getProject();
        return new ProjectListModel(
                project.getName(),
                userProject.getAccess_level());
    }

    private static PrivilegeModel privilegeToPrivilegeModel(Privilege privilege) {
        return new PrivilegeModel(privilege.getName(), privilege.getDescription(), privilege.isInternal());
    }

    @Override
    public List<UserModel> getAllUsers() {
        return ((List<User>) userRepository.findAll()).stream().map(
                UserServiceImpl::userToUserModel
        ).collect(Collectors.toList());
    }

    @Override
    public UserModel getUserByUserName(String username) throws UsernameNotFoundException, UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        else return userToUserModel(user);
    }

    @Override
    @Transactional
    public UserModel createUser(UserModel userModel) {
        User user = userRepository.findByUsername(userModel.getUsername());
        if (user != null) throw new UserAlreadyExistsException();
        // Construct user entity from information
        List<Privilege> privileges = privilegeRepository.findAllByNameIn(userModel.getPrivileges());
        PasswordHash passwordHash = new PasswordHash();
        user = new User(
                userModel.getUsername(),
                passwordHash.hashPassword(userModel.getPassword()),
                userModel.getEmail(),
                privileges,
                null
        );
        userRepository.save(user);
        return userModel;
    }

    @Override
    @Transactional
    public UserModel updateUser(UserModel userModel) {
        User user = userRepository.findByUsername(userModel.getUsername());
        if (user == null) throw new UserNotFoundException();
        // .save performs both update and creation
        // Construct user entity from information
        List<Privilege> privileges = privilegeRepository.findAllByNameIn(userModel.getPrivileges());
        PasswordHash passwordHash = new PasswordHash();
        user = new User(
                userModel.getUsername(),
                passwordHash.hashPassword(userModel.getPassword()),
                userModel.getEmail(),
                privileges,
                null
        );
        userRepository.save(user);
        return userModel;
    }

    @Override
    @Transactional
    public UserModel deleteUser(String username) {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException();
        Logger logger = Logger.getAnonymousLogger();
        logger.info(username);
        userRepository.deleteByUsername(username);
        return null;
    }

    @Override
    public List<PrivilegeModel> getAllPrivileges() {
        return ((List<Privilege>) privilegeRepository.findAll())
                .parallelStream()
                .map(UserServiceImpl::privilegeToPrivilegeModel)
                .collect(Collectors.toList());
    }

    // Method used by spring security for providing user information
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) throw new UserNotFoundException();
        else return new UserAdapter(user);
    }
}
