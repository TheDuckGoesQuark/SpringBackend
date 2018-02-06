package BE.security;

public class SecurityUtils {

    static final String NO_GRANT_TYPE_ERROR_MESSAGE = "No grant_type given.";
    static final String NO_USERNAME_OR_PASSWORD_ERROR_MESSAGE = "No username or password given.";
    static final String NO_REFRESH_TOKEN_ERROR_MESSAGE = "No refresh_token supplied.";
    static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid credentials - username or password incorrect.";

    public static void validateRequestStructure(String username, String password, String grant_type, String refresh_token) {
        // Check grant type is supplied
        if (grant_type == null)
            throw new CustomAuthenticationException(NO_GRANT_TYPE_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);

        // Check all necessary fields are supplied for given grant type
        if (grant_type.equals(GrantTypes.PASSWORD.toString()) && (username == null || password == null)) {
            throw new CustomAuthenticationException(NO_USERNAME_OR_PASSWORD_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);
        } else if (grant_type.equals(GrantTypes.REFRESH_TOKEN.toString()) && refresh_token == null) {
            throw new CustomAuthenticationException(NO_REFRESH_TOKEN_ERROR_MESSAGE, AuthenticationFailureType.INVALID_REQUEST);
        } else {
            throw new CustomAuthenticationException(AuthenticationFailureType.UNSUPPORTED_GRANT_TYPE.toString(),
                    AuthenticationFailureType.UNSUPPORTED_GRANT_TYPE);
        }
    }
}
