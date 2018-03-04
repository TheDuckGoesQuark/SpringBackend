package BE.repositories;

import BE.entities.project.MetaFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends CrudRepository<MetaFile, Integer> {

    public MetaFile findByProjectName(String projectName);

    @Query(
            value = "SELECT * FROM file WHERE file_id = file.file_id",
            nativeQuery = true
    )
    MetaFile findByFileId(@Param("file_id") int file_id);

}
