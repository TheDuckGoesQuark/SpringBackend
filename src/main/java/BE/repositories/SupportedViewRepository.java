package BE.repositories;

import BE.entities.project.SupportedView;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportedViewRepository extends CrudRepository<SupportedView, String> {
    SupportedView findByView(String viewName);
}
