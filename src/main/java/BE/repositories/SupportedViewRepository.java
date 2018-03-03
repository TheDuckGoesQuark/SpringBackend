package BE.repositories;

import BE.entities.project.SupportedView;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportedViewRepository extends CrudRepository<SupportedView, SupportedView> {
    List<SupportedView> findByMetaFile_FileId(int file_Id);

    SupportedView findByView(String viewName);
}
