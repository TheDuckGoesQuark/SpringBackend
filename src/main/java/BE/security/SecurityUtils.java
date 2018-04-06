package BE.security;

import BE.exceptions.excludedFromBaseException.AuthenticationException;
import BE.security.enums.AuthenticationFailureType;
import BE.security.enums.GrantTypes;

public class SecurityUtils {

    private static final String NO_GRANT_TYPE_ERROR_MESSAGE = "No grant_type given.";
    private static final String NO_USERNAME_OR_PASSWORD_ERROR_MESSAGE = "No username or password given.";
    private static final String NO_REFRESH_TOKEN_ERROR_MESSAGE = "No refresh_token supplied.";
    public static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid credentials - username or password incorrect.";

    public static final String AUTHORISATION_HEADER = "Authorisation";
    public static final String OAUTH_AUTHORISATION_HEADER = "Authorization";
    public static final String TOKEN_BEARER_STRING_FOR_APPENDING = "Bearer ";

    public static void validateRequestStructure(String username, String password, String grant_type, String refresh_token) {
        // Check grant type is supplied
        if (grant_type == null)
            throw new AuthenticationException(NO_GRANT_TYPE_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);

        // Check all necessary fields are supplied for given grant type
        if (grant_type.equals(GrantTypes.PASSWORD.toString())) {
            if ((username == null || password == null))
                throw new AuthenticationException(NO_USERNAME_OR_PASSWORD_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);
        } else if (grant_type.equals(GrantTypes.REFRESH_TOKEN.toString())) {
            if (refresh_token == null) throw new AuthenticationException(NO_REFRESH_TOKEN_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);
        } else {
            throw new AuthenticationException(
                    AuthenticationFailureType.UNSUPPORTED_GRANT_TYPE.toString(),
                    AuthenticationFailureType.UNSUPPORTED_GRANT_TYPE);
        }
    }
}
