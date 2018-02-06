package BE.security;

import BE.responsemodels.security.TokenModel;
import BE.responsemodels.security.TokenRequestModel;
import BE.responsemodels.user.UserModel;
import BE.services.TokenService;
import BE.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static BE.security.FilterUtils.validateRequestStructure;

/**
 * Handles initial request for token i.e. through username and password
 */
public class CustomFormAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CHARACTER_ENCODING_UTF_8 = "UTF-8";

    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public CustomFormAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, UserService userService, TokenService tokenService) {
        super(requiresAuthenticationRequestMatcher);
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String grant_type = request.getParameter("grant_type");

        // Validate structure
        validateRequestStructure(username, password, grant_type, null);

/*        // Validate user credentials TODO include hash
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null || !user.getPassword().equals(password))
            throw new CustomAuthenticationException(INVALID_CREDENTIALS_ERROR_MESSAGE, AuthenticationFailureType.INVALID_GRANT);*/

        // Create token object for credentials
        TokenRequestModel tokenModel = new TokenRequestModel(grant_type, null, username, password);
        return this.getAuthenticationManager().authenticate(tokenModel);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        TokenModel tokenModel = tokenService.allocateToken((String) authentication.getPrincipal());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(tokenModel));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);
        JSONObject jsonResponse = ((CustomAuthenticationException) failed).getErrorResponse();
        response.getWriter().write(jsonResponse.toString());
    }
}
