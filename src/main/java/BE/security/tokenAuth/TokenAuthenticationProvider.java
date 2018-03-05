package BE.security.tokenAuth;

import BE.responsemodels.security.TokenHeaderModel;
import BE.services.TokenService;
import BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final
    TokenService tokenService;

    private final
    UserService userService;

    @Autowired
    public TokenAuthenticationProvider(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    /**
     * Authenticates a token
     * @param authentication
     * @return token
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String access_token = (String) authentication.getCredentials();
        String username = tokenService.getUsernameFromTokenId(access_token);
        UserDetails userDetails = userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenHeaderModel.class.isAssignableFrom(authentication);
    }
}
