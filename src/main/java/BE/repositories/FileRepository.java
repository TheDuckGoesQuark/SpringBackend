package BE.repositories;

import BE.entities.project.MetaFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface FileRepository extends CrudRepository<MetaFile, Integer> {

    @Query(
            value = "SELECT f.* FROM file f INNER JOIN project p ON p.root_dir_id = f.file_id WHERE p.name = :project_name",
            nativeQuery = true
    )
    MetaFile getProjectRootDir(@Param("project_name") String projectName);

    @Query(
            value = "SELECT * FROM file WHERE :file_id = file.file_id",
            nativeQuery = true
    )
    MetaFile findByFileId(@Param("file_id") int file_id);

    @Procedure(name="insertFile")
    void saveNew(String file_path,
                 String name,
                 String file_type,
                 String file_status,
                 long file_length,
                 String project_name );
}
