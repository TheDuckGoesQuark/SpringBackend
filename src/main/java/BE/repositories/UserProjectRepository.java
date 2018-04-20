package BE.repositories;

import BE.entities.UserProject;
import BE.entities.project.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserProjectRepository extends CrudRepository<UserProject, String> {
    List<UserProject> findAll();
}
