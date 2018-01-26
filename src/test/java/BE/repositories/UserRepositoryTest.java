package BE.repositories;

import BE.models.user.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        UserModel user1 = new UserModel("JohnSmith", "password1");
        UserModel user2 = new UserModel("SomeGuy", "password2");
        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        UserModel userA = userRepository.findByUsername("JohnSmith");
        assertTrue(userA != null);
        assertTrue(userA.getPassword().equals("password1"));
        /*Get all products, list should only have two*/
        Iterable<UserModel> users = userRepository.findAll();
        AtomicInteger count = new AtomicInteger(0);
        for(UserModel p : users){
            count.getAndIncrement();
        }
        assertEquals(count.get(), 2);
    }
}