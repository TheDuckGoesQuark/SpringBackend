package BE.repositories;

import BE.entities.project.tabular.Header;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ColumnHeaderRepository extends CrudRepository<Header, Header.HeaderPK> {
    List<Header> getAllByIdFileid(int file_id);
}
