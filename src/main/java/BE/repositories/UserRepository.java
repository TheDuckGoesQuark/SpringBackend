package BE.repositories;

import BE.models.user.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

    UserModel findByUsername(String username);

    UserModel deleteByUsername(String username);
}
