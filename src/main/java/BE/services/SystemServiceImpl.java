package BE.services;

import BE.controllers.LoggingLevel;
import BE.controllers.PropertyValue;
import BE.entities.system.Logging;
import BE.entities.system.Property;
import BE.exceptions.*;
import BE.models.system.LoggingModel;
import BE.models.system.PropertyModel;
import BE.repositories.LoggingRepository;
import BE.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService{

    private final
    LoggingRepository loggingRepository;

    private final
    PropertyRepository propertyRepository;

    @Autowired
    public SystemServiceImpl(LoggingRepository loggingRepository, PropertyRepository propertyRepository) {
        this.loggingRepository = loggingRepository;
        this.propertyRepository = propertyRepository;
    }

    private static LoggingModel loggingToLoggingModel(Logging logging) {
        return new LoggingModel(logging.getComponent(), logging.getLevel(), logging.getValue(), logging.getUsername(), logging.getTimestamp());
    }

    private static PropertyModel propertyToPropertyModel(Property property) {
        return new PropertyModel(property.getId(), property.isReadonly(), property.getType(), property.getValue());
    }

    @Override
    public LoggingModel storeLog(LoggingModel loggingModel, String username) {
        Logging log = loggingRepository.findByValue(loggingModel.getValue());
        if (log != null) throw new LogValueAlreadyExistsException();
        String level = loggingModel.getLevel();
        if (!level.equals(LoggingLevel.INFO) &&
                !level.equals(LoggingLevel.SECURITY) &&
                !level.equals(LoggingLevel.WARNING) &&
                !level.equals(LoggingLevel.ERROR) &&
                !level.equals(LoggingLevel.CRITICAL)) throw new InvalidLoggingLevelException();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        dateFormat.setTimeZone(timeZone);
        String timestamp = dateFormat.format(new Date());
        Logging logging = new Logging(loggingModel.getComponent(),
                loggingModel.getLevel(),
                loggingModel.getValue(),
                username,
                timestamp);
        loggingRepository.save(logging);
        return loggingModel;
    }

    @Override
    public List<LoggingModel> retrieveLogs(String beforeDate, String afterDate, String level) throws ParseException {
        List<Logging> logs;
        if (level == null) {
            logs = (List<Logging>) loggingRepository.findAll();
        } else {
            if (!level.equals(LoggingLevel.INFO) &&
                    !level.equals(LoggingLevel.SECURITY) &&
                    !level.equals(LoggingLevel.WARNING) &&
                    !level.equals(LoggingLevel.ERROR) &&
                    !level.equals(LoggingLevel.CRITICAL)) throw new InvalidLoggingLevelException();
            logs = loggingRepository.findByLevel(level);
        }
        List<LoggingModel> loggingModels = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        Date bD;
        Date aD;
        if (beforeDate == null) {
            bD = dateFormat.parse("3000-01-01T00:00Z");
        } else {
            bD = dateFormat.parse(beforeDate);
        }
        if (afterDate == null) {
            aD = dateFormat.parse("2000-01-01T00:00Z");
        } else {
            aD = dateFormat.parse(afterDate);
        }
        for (int i = 0; i < logs.size(); i++) {
            Logging logging = logs.get(i);
            Date loggingTZ = dateFormat.parse(logging.getTimestamp());
            if (loggingTZ.before(bD) && loggingTZ.after(aD)) {
                loggingModels.add(loggingToLoggingModel(logging));
            }
        }
        return loggingModels;
    }

    @Override
    public List<PropertyModel> getProperties() {
        return ((List<Property>) propertyRepository.findAll()).stream().map(
                SystemServiceImpl::propertyToPropertyModel
        ).collect(Collectors.toList());
    }

    @Override
    public PropertyModel updateProperties(PropertyModel propertyModel) {
        Property property = propertyRepository.findById(propertyModel.getId());
        if (property == null || property.isReadonly()) throw new InvalidPropertyException();
        property = new Property(
                propertyModel.getId(),
                propertyModel.isReadonly(),
                PropertyValue.STRING,
                propertyModel.getValue()
        );
        propertyRepository.save(property);
        return propertyToPropertyModel(property);
    }

    @Override
    public void createProperty(PropertyModel propertyModel) {
        Property property = new Property(propertyModel.getId(), propertyModel.isReadonly(), propertyModel.getType(), propertyModel.getValue());
        propertyRepository.save(property);
    }

    @Override
    public Boolean propertyExists(String id) {
        return propertyRepository.exists(id);
    }
}
