package BE.repositories;

import BE.entities.project.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, String> {

    public Project findByName(String name);

    public void deleteByName(String name);

}
