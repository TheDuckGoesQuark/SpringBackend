package BE.services;

import BE.models.system.LoggingModel;

import java.text.ParseException;
import java.util.List;

public interface SystemService {

    LoggingModel storeLog(LoggingModel loggingModel, String username);

    List<LoggingModel> retrieveLogs(String beforeDate, String afterDate, String level) throws ParseException;
}
