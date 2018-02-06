package BE.security.passwordAuth;

import BE.exceptions.excludedFromBaseException.AuthenticationException;
import BE.security.enums.AuthenticationFailureType;
import BE.security.SecurityUtils;
import BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Autowired
    public FormAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws org.springframework.security.core.AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails user = userService.loadUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException(
                    SecurityUtils.INVALID_CREDENTIALS_ERROR_MESSAGE,
                    AuthenticationFailureType.INVALID_GRANT
            );
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
