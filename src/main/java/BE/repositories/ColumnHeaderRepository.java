package BE.repositories;

import BE.entities.project.tabular.Header;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ColumnHeaderRepository extends CrudRepository<Header, Integer> {
    List<Header> getAllByFileOrderByIndexAsc(int file_id);
}
