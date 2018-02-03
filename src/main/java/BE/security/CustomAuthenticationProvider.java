package BE.security;

import BE.responsemodels.security.TokenRequestModel;
import BE.services.TokenService;
import BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    final
    UserService userService;

    final
    TokenService tokenService;

    @Autowired
    public CustomAuthenticationProvider(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenRequestModel.class.isAssignableFrom(authentication);
    }
}
