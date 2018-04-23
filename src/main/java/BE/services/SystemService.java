package BE.services;

import BE.models.system.LoggingModel;
import BE.models.system.PropertyModel;

import java.text.ParseException;
import java.util.List;

public interface SystemService {

    LoggingModel storeLog(LoggingModel loggingModel, String username);

    List<LoggingModel> retrieveLogs(String beforeDate, String afterDate, String level) throws ParseException;

    List<PropertyModel> getProperties();

    PropertyModel updateProperties(PropertyModel propertyModel);

    void createProperty(PropertyModel propertyModel);

    Boolean propertyExists(String id);
}
