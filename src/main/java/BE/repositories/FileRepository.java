package BE.repositories;

import BE.entities.project.MetaFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends CrudRepository<MetaFile, Integer> {

    MetaFile findByFileId(int file_id);

    MetaFile findByParent(MetaFile parent);

    @Query(value = "SELECT file.path FROM file WHERE file.file_id = :fileid",
            nativeQuery = true)
    String getPathFromId(@Param("fileid") int file_id);

}
