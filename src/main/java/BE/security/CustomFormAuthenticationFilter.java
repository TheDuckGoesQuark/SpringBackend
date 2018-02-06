package BE.security;

import BE.exceptions.AuthorisationFailureTypes;
import BE.exceptions.NotAuthorisedException;
import BE.responsemodels.security.TokenRequestModel;
import BE.services.TokenService;
import BE.services.UserService;
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

public class CustomFormAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String CHARACTER_ENCODING_UTF_8 = "UTF-8";

    private UserService userService;
    private final TokenService tokenService;

    @Autowired
    public CustomFormAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, UserService userService, TokenService tokenService) {
        super(requiresAuthenticationRequestMatcher);
        this.userService = userService;
        this.tokenService = tokenService;
    }

    private static void validateRequestStructure(String username, String password, String grant_type, String refresh_token) {
        // Check grant type is supplied
        if (grant_type == null) throw new CustomAuthenticationException(
                new NotAuthorisedException(AuthorisationFailureTypes.INVALID_REQUEST));

        // Check all necessary fields are supplied for given grant type
        if (grant_type.equals(GrantTypes.PASSWORD.toString()) && (username == null || password == null)) {
            throw new CustomAuthenticationException(
                    new NotAuthorisedException(AuthorisationFailureTypes.INVALID_REQUEST));
        } else if (grant_type.equals(GrantTypes.REFRESH_TOKEN.toString()) && refresh_token == null) {
            throw new CustomAuthenticationException(
                    new NotAuthorisedException(AuthorisationFailureTypes.INVALID_REQUEST));
        } else {
            throw new CustomAuthenticationException(
                    new NotAuthorisedException(AuthorisationFailureTypes.UNSUPPORTED_GRANT_TYPE));
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String grant_type = request.getParameter("grant_type");
        String refresh_token = request.getParameter("refresh_token");

        // Validate structure
        validateRequestStructure(username, password, grant_type, refresh_token);

        // Validate user credentials TODO include hash
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) throw new CustomAuthenticationException(
                new NotAuthorisedException(AuthorisationFailureTypes.INVALID_GRANT));

        // Allocate token
        TokenRequestModel tokenModel = new TokenRequestModel(grant_type, refresh_token, username, password);
        return this.getAuthenticationManager().authenticate(tokenModel);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);


        JSONObject jsonResponse = new JSONObject();
        String username = (String) authentication.getPrincipal();
        jsonResponse.put("message", "Login Successful");

        response.getWriter().write(jsonResponse.toString());
        response.setHeader(JwtUtil.HEADER_APP_SUBJECT, authentication.getName());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "Invalid Credentials");
        response.getWriter().write(jsonResponse.toString());
    }
}
}
