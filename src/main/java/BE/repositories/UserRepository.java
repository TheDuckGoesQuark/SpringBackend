package BE.repositories;

import BE.models.user.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends CrudRepository<UserModel, Integer> {

    UserModel findByUsername(String username);

    @Transactional
    UserModel deleteByUsername(String username);
}
