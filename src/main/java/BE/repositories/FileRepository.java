package BE.repositories;

import BE.entities.project.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends CrudRepository<File, Integer> {

    public File findByProjectName(String projectName);

//    @Query(
//            value = "SELECT * FROM file WHERE file_id = file.file_id",
//            nativeQuery = true
//    )
//    File findByFileId(@Param("file_id") int file_id);

}
