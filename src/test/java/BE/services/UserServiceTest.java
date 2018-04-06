package BE.services;

import BE.MockData;
import BE.entities.UserProject;
import BE.entities.user.Privilege;
import BE.entities.user.User;
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
    public void getAllUsersWhenEmpty() throws Exception {
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
    public void getAllUsersWhenUsersExist() throws Exception {
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
}
