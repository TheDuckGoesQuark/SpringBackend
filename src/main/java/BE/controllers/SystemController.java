package BE.controllers;

import BE.exceptions.NotImplementedException;
import BE.responsemodels.system.LoggingModel;
import BE.responsemodels.system.SupportedProtocolListModel;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public LoggingModel postLog(@RequestBody LoggingModel loggingModel) {
        throw new NotImplementedException();
    }

}
