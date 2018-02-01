package BE.controllers;

import BE.responsemodels.system.SupportedProtocolListModel;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class SystemController {

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

}
