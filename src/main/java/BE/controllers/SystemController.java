package BE.controllers;

import BE.exceptions.NotImplementedException;
import BE.responsemodels.system.LoggingModel;
import BE.responsemodels.system.PropertyModel;
import BE.responsemodels.system.SupportedProtocolListModel;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class SystemController {

    private static final Logger logger = Logger.getLogger(SystemController.class);

    private static String[] SUPPORTED_PROTOCOLS = {"BE01"};
    private static String[] REQUIRED_PROTOCOLS = {"BE01"};
    private static final SupportedProtocolListModel SUPPORTED_PROTOCOL_LIST = new SupportedProtocolListModel(
            Arrays.asList(SUPPORTED_PROTOCOLS),
            Arrays.asList(REQUIRED_PROTOCOLS)
    );

    @RequestMapping(value = "/_supported_protocols_", method = RequestMethod.GET)
    public SupportedProtocolListModel getSupportedProtocols() {
        return SUPPORTED_PROTOCOL_LIST;
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public List<LoggingModel> getLogs(@RequestParam(value = "before", required = false) String beforeData,
                                      @RequestParam(value = "after", required = false) String afterDate,
                                      @RequestParam(value = "level", required = false) String level) {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public LoggingModel postLog(@RequestBody LoggingModel loggingModel) {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public List<PropertyModel> getProperties() {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/properties", params = {"action=update"}, method = RequestMethod.POST)
    public PropertyModel updateProperties(@RequestBody PropertyModel propertyModel) {
        throw new NotImplementedException();
    }

}
