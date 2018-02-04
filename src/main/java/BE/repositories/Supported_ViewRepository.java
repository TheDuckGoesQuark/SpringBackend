package BE.repositories;

import BE.entities.project.Supported_view;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Supported_ViewRepository extends CrudRepository<Supported_view, Supported_view> { // may have to be Supported_view_PK class
    List<Supported_view> findByFileFileId(int file_Id);
}
