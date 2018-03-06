package BE.repositories;

import BE.entities.project.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {

    Project findByName(String name);

    void deleteByName(String name);

}
