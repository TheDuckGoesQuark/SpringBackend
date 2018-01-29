package BE.repositories;

import BE.entities.project.File;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<File, Integer> {

    public List<File> findByProjectName(String projectName);

}
