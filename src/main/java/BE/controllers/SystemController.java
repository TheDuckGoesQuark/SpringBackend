package BE.controllers;

import BE.exceptions.NotImplementedException;
import BE.models.system.LoggingModel;
import BE.models.system.PropertyModel;
import BE.models.system.SupportedProtocolListModel;
import BE.services.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class SystemController {

    private static final Logger logger = Logger.getLogger(SystemController.class);

    private static String[] SUPPORTED_PROTOCOLS = {"BE01", "BE02", "BE50", "BE70"};
    private static String[] REQUIRED_PROTOCOLS = {"BE01", "BE02"};
    private static final SupportedProtocolListModel SUPPORTED_PROTOCOL_LIST = new SupportedProtocolListModel(
            Arrays.asList(SUPPORTED_PROTOCOLS),
            Arrays.asList(REQUIRED_PROTOCOLS)
    );

    @Autowired
    private SystemService systemService;

    /**
     * Gets a list of all supported protocols
     * @return a list of protocols
     */
    @RequestMapping(value = "/_supported_protocols_", method = RequestMethod.GET)
    public SupportedProtocolListModel getSupportedProtocols() {
        return SUPPORTED_PROTOCOL_LIST;
    }

    /**
     * Gets a list of all system logs
     * @param beforeDate
     * @param afterDate
     * @param level
     * @return a list of all system logs
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public List<LoggingModel> getLogs(@RequestParam(value = "before", required = false) String beforeDate,
                                      @RequestParam(value = "after", required = false) String afterDate,
                                      @RequestParam(value = "level", required = false) String level) throws ParseException{
        return systemService.retrieveLoggings(beforeDate, afterDate, level);
    }

    /**
     * Creates a new system log
     * @param loggingModel the new log
     * @return log
     */
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public LoggingModel postLog(Principal principal, @RequestBody LoggingModel loggingModel) {
        String username = principal.getName();
        return systemService.storeLogging(loggingModel, username);
    }

    /**
     * Gets all of the properties of the system
     * @return a list of all properties
     */
    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public List<PropertyModel> getProperties() {
        throw new NotImplementedException();
    }

    /**
     * Updates the properties of the system
     * @param propertyModel the new property
     * @return property
     */
    @RequestMapping(value = "/properties", params = {"action="+Action.UPDATE}, method = RequestMethod.POST)
    public PropertyModel updateProperties(@RequestBody PropertyModel propertyModel) {
        throw new NotImplementedException();
    }

}
