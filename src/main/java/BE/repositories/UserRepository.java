package BE.repositories;

import BE.entities.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    User deleteByUsername(String username);
}
