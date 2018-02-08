package BE.services;

import BE.entities.UserProject;
import BE.entities.user.Privilege;
import BE.entities.user.User;
import BE.repositories.UserRepository;
import BE.responsemodels.user.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;

import static org.junit.Assert.assertTrue;
/**
 * Testing UserServiceImpl without external repository
 */

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserService userService;

    // Mock repository
    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        Privilege[] privileges = new Privilege[1];
        privileges[0] = new Privilege("admin", "can do anything", true);
        UserProject[] userProjects = new UserProject[0];
        User alex = new User("alex", "password", "alex@isaguy.com", Arrays.asList(privileges), Arrays.asList(userProjects));
        // When this function call is made, return this. i.e. imitate database connection
        Mockito.when(userRepository.findByUsername(alex.getUsername()))
                .thenReturn(alex);
    }

    @Test
    public void validNameReturnsValidUser() {
        String username = "alex";
        UserModel found = userService.getUserByUserName(username);
        assertTrue(found.getUsername().equals(username));
    }


}
