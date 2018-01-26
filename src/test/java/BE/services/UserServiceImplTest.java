package BE.services;

import BE.models.user.UserModel;
import BE.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
/**
 * Testing UserServiceImpl without external repository
 */

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    // Mock repository
    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        UserModel alex = new UserModel("alex", "password");
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
