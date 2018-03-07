package BE.repositories;

import BE.entities.project.MetaFile;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<MetaFile, Integer> {
    MetaFile findByFileId(int file_id);
    MetaFile findByParent(MetaFile parent);
}
