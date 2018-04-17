package BE.services;

import BE.MockData;
import BE.entities.UserProject;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.exceptions.UserAlreadyExistsException;
import BE.exceptions.UserNotFoundException;
import BE.repositories.PrivilegeRepository;
import BE.repositories.UserRepository;
import BE.models.user.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing UserServiceImpl without external repository
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
    }

    @Test
    public void getAllUsersWhenEmpty() {
        // Given
        List<User> users = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(users);
        // When
        List<UserModel> userModels = userService.getAllUsers();
        // Then
        assertTrue(userModels != null);
        assertTrue(userModels.size() == 0);

        verify(userRepository).findAll();
    }

    @Test
    public void getAllUsersWhenUsersExist() {
        // Given
        List<User> users = Arrays.asList(MockData.USERS);
        when(userRepository.findAll()).thenReturn(users);
        // When
        List<UserModel> userModels = userService.getAllUsers();
        // Then
        assertTrue(userModels != null);
        assertTrue(userModels.size() == users.size());
        for (User user : users) {
            assertTrue(userModels.stream().anyMatch(
                    userModel -> userModel.getUsername().equals(user.getUsername())
                            && userModel.getPassword().equals(user.getPassword())
                            && userModel.getEmail().equals(user.getEmail())
            ));
        }

        verify(userRepository).findAll();
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserThatDoesntExist() {
        // Given
        when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
        // When
        UserModel userModel = userService.getUserByUserName("Jackson5");
        // Then
        // Expect exception
    }

    @Test
    public void getUserThatDoesExist() {
        // Given
        List<User> users = Arrays.asList(MockData.USERS);
        User user = users.get(0);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // When
        UserModel userModel = userService.getUserByUserName(user.getUsername());

        // Then
        assertTrue(userModel != null);
        assertTrue(userModel.getUsername().equals(user.getUsername())
                        && userModel.getPassword().equals(user.getPassword())
                        && userModel.getEmail().equals(user.getEmail())
        );

        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    public void createUser() {
        // Given
        List<User> users = Arrays.asList(MockData.USERS);
        User user = users.get(0);
        user.setPrivileges(null);
        user.setUserProjects(null);
        when(userRepository.save((User) Mockito.any())).thenReturn(
                new User(user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getPrivileges(),
                        user.getUserProjects())
        );

        // When
        UserModel userModel = new UserModel(user.getUsername(), user.getPassword(), user.getEmail(), null, null);
        userModel = userService.createUser(userModel);

        // Then
        assertTrue(userModel != null);
        assertTrue(userModel.getUsername().equals(user.getUsername())
                && userModel.getEmail().equals(user.getEmail())
        );

        verify(userRepository).save((User) Mockito.any());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createExistingUser() {
        // Given
        List<User> users = Arrays.asList(MockData.USERS);
        User user = users.get(0);
        user.setPrivileges(null);
        user.setUserProjects(null);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // When
        UserModel userModel = new UserModel(user.getUsername(), user.getPassword(), user.getEmail(), null, null);
        userModel = userService.createUser(userModel);

        // Then
        // Expect exception
    }
}
