package BE.repositories;

import BE.entities.system.Logging;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoggingRepository extends CrudRepository<Logging, String> {

    List<Logging> findByLevel(String level);

}
