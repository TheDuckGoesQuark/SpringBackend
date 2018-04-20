package BE.repositories;

import BE.entities.project.Project;
import BE.entities.project.Role;
import BE.entities.project.tabular.Header;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, String> {
    List<Role> findAll();
}
