package BE.repositories;

import BE.entities.project.Supported_view;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Supported_ViewRepository extends CrudRepository<Supported_view, Supported_view> {
    List<Supported_view> findByFileFileId(int file_Id);
}
