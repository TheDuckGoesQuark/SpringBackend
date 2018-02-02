package BE.repositories;

import BE.entities.user.Privilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege, String> {

    Privilege findByName(String name);

    @Query(
            value = "SELECT p FROM privilege p WHERE p.name IN :names",
            nativeQuery = true
    )
    List<Privilege> findAllByNameIn(@Param("names") List<String> names);

}
