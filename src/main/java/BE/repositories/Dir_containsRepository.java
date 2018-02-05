package BE.repositories;

import BE.entities.project.Dir_contains;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Dir_containsRepository extends CrudRepository<Dir_contains, Dir_contains> {
    List<Dir_contains> findByDirId(int dir_id);
}
