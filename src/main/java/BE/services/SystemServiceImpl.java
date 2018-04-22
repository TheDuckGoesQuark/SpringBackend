package BE.services;

import BE.entities.system.Logging;
import BE.models.system.LoggingModel;
import BE.repositories.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class SystemServiceImpl implements SystemService{

    private final
    LoggingRepository loggingRepository;

    @Autowired
    public SystemServiceImpl(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    private static LoggingModel loggingToLoggingModel(Logging logging) {
        return new LoggingModel(logging.getComponent(), logging.getLevel(), logging.getValue(), logging.getUsername(), logging.getTimestamp());
    }

    @Override
    public LoggingModel storeLogging(LoggingModel loggingModel, String username) {
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
    public List<LoggingModel> retrieveLoggings(String beforeDate, String afterDate, String level) throws ParseException {
        List<Logging> loggings = loggingRepository.findByLevel(level);
        List<LoggingModel> loggingModels = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        Date bD = dateFormat.parse(beforeDate);
        Date aD = dateFormat.parse(afterDate);
        for (int i = 0; i < loggings.size(); i++) {
            Logging logging = loggings.get(i);
            Date loggingTZ = dateFormat.parse(logging.getTimestamp());
            if (loggingTZ.before(bD) && loggingTZ.after(aD)) {
                loggingModels.add(loggingToLoggingModel(logging));
            }
        }
        return loggingModels;
    }
}
