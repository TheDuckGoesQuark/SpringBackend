package BE.repositories;

import BE.entities.project.tabular.RowCount;
import org.springframework.data.repository.CrudRepository;

public interface RowCountRepository extends CrudRepository<RowCount, Integer> {

    RowCount findByFile(int file_id);

}
