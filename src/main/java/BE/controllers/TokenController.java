package BE.controllers;

import BE.responsemodels.security.TokenModel;
import BE.responsemodels.security.TokenRequestModel;
import BE.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oauth")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public TokenModel getToken(@RequestBody TokenRequestModel tokenRequestModel) {
        return null;
    }
}
