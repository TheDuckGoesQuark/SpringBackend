package BE.security.passwordAuth;

import BE.responsemodels.security.TokenModel;
import BE.responsemodels.security.TokenRequestModel;
import BE.exceptions.excludedFromBaseException.AuthenticationException;
import BE.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static BE.security.SecurityUtils.validateRequestStructure;

/**
 * Handles initial request for token i.e. through username and password (or possibly refresh_tokens too)
 */
public class FormAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CHARACTER_ENCODING_UTF_8 = "UTF-8";

    private TokenService tokenService;


    @Autowired
    public FormAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, TokenService tokenService) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenService = tokenService;
    }

    /**
     * Attempts to authenticate a user from a request
     * @param request the request that contains the user information
     * @param response
     * @return token
     * @throws org.springframework.security.core.AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws org.springframework.security.core.AuthenticationException, IOException, ServletException {
        String username = request.getParameter(URLEncoder.encode("username",CHARACTER_ENCODING_UTF_8));
        String password = request.getParameter(URLEncoder.encode("password", CHARACTER_ENCODING_UTF_8));
        String grant_type = request.getParameter(URLEncoder.encode("grant_type",CHARACTER_ENCODING_UTF_8));
        // Validate structure
        validateRequestStructure(username, password, grant_type, null);

        // Create token object for credentials
        TokenRequestModel tokenModel = new TokenRequestModel(grant_type, null, username, password);
        return this.getAuthenticationManager().authenticate(tokenModel);
    }

    /**
     * Successful authentication. Allocates user a token
     * @param request the successful request
     * @param response
     * @param chain
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
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

    /**
     * Unsuccessful authentication. Responds to inform user of failure
     * @param request the failed request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);
        JSONObject jsonResponse = ((AuthenticationException) failed).getErrorResponse();
        response.getWriter().write(jsonResponse.toString());
    }
}
