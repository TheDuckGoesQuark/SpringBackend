package BE.services;

import BE.models.system.LoggingModel;

import java.text.ParseException;
import java.util.List;

public interface SystemService {

    LoggingModel storeLogging(LoggingModel loggingModel, String username);

    List<LoggingModel> retrieveLoggings(String beforeDate, String afterDate, String level) throws ParseException;
}
