package BE.repositories;

import BE.entities.UserProject;
import org.springframework.data.repository.CrudRepository;

public interface UserProjectRepository extends CrudRepository<UserProject, String> {

//    UserProject findByUsernameAndProjectName(String username, String project_name);
//
//    void deleteByUsernameAndProjectName(String username, String project_name);
}
