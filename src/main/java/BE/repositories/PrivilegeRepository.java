package BE.repositories;

import BE.entities.user.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, String> {

    Privilege findByName(String name);

}
