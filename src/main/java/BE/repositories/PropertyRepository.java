package BE.repositories;

import BE.entities.system.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, String> {

    Property findById(String id);
}
