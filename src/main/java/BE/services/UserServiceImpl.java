package BE.services;

import BE.entities.UserProject;
import BE.entities.project.Project;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.exceptions.UserNotFoundException;
import BE.exceptions.UserAlreadyExistsException;
import BE.repositories.PrivilegeRepository;
import BE.repositories.UserRepository;
import BE.models.user.PrivilegeModel;
import BE.models.user.ProjectListModel;
import BE.models.user.UserModel;
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

    /**
     * Converts a specific user to a user model
     * @param user the user to be converted
     * @return user model
     */
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

    /**
     * Converts a specific user project to a project list model
     * @param userProject the user project to be converted
     * @return project list model
     */
    private static ProjectListModel userProjectToProjectListModel(UserProject userProject) {
        Project project = userProject.getProject();
        return new ProjectListModel(
                project.getName(),
                userProject.getAccess_level());
    }

    /**
     * Converts a specific privilege to a privilege model
     * @param privilege the privilege to be converted
     * @return privilege model
     */
    private static PrivilegeModel privilegeToPrivilegeModel(Privilege privilege) {
        return new PrivilegeModel(privilege.getName(), privilege.getDescription(), privilege.isInternal());
    }

    /**
     * Gets all users
     * @return a list of all users
     */
    @Override
    public List<UserModel> getAllUsers() {
        return ((List<User>) userRepository.findAll()).stream().map(
                UserServiceImpl::userToUserModel
        ).collect(Collectors.toList());
    }

    /**
     * Gets specific user by their username
     * @param username username of the user to get
     * @return user
     * @throws UsernameNotFoundException
     * @throws UserNotFoundException
     */
    @Override
    public UserModel getUserByUserName(String username) throws UsernameNotFoundException, UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException();
        else return userToUserModel(user);
    }

    /**
     * Creates new user
     * @param userModel the user to create
     * @return user
     */
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

    /**
     * Updates a specific existing user
     * @param userModel updated user
     * @return user
     */
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

    /**
     * Deletes a specific existing user
     * @param username user to delete
     * @return user
     */
    @Override
    @Transactional
    public UserModel deleteUser(String username) {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException();
        Logger logger = Logger.getAnonymousLogger();
        logger.info(username);
        userRepository.deleteByUsername(username);
        return null;
    }

    /**
     * Gets all user privileges
     * @return a list of all user privileges
     */
    @Override
    public List<PrivilegeModel> getAllPrivileges() {
        return ((List<Privilege>) privilegeRepository.findAll())
                .parallelStream()
                .map(UserServiceImpl::privilegeToPrivilegeModel)
                .collect(Collectors.toList());
    }

    /**
     * Method used by spring security for providing user information
     * @param s username of user
     * @return user details
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) throw new UserNotFoundException();
        else return new UserAdapter(user);
    }
}
