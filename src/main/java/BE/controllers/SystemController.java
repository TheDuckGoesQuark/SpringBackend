package BE.controllers;

import BE.exceptions.NotImplementedException;
import BE.models.system.LoggingModel;
import BE.models.system.PropertyModel;
import BE.models.system.SupportedProtocolListModel;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

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
     * @param beforeData
     * @param afterDate
     * @param level
     * @return a list of all system logs
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public List<LoggingModel> getLogs(@RequestParam(value = "before", required = false) String beforeData,
                                      @RequestParam(value = "after", required = false) String afterDate,
                                      @RequestParam(value = "level", required = false) String level) {
        throw new NotImplementedException();
    }

    /**
     * Creates a new system log
     * @param loggingModel the new log
     * @return log
     */
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public LoggingModel postLog(@RequestBody LoggingModel loggingModel) {
        throw new NotImplementedException();
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
