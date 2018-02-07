package BE.repositories;

import BE.entities.project.Dir_contains;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Dir_containsRepository extends CrudRepository<Dir_contains, Dir_contains> {
    @Query("SELECT child FROM Dir_contains child WHERE child.dir.fileId = :dir_id")
    List<Dir_contains> findByDirId(@Param("dir_id") int dir_id);
}
