package BE.repositories;

import BE.entities.project.tabular.Header;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ColumnHeaderRepository extends CrudRepository<Header, Header.HeaderPK> {
    List<Header> getAllById_File_FileId(int file_id);
}
