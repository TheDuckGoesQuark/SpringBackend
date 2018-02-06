package BE.security;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    
    private AuthenticationFailureType authenticationFailureType;

    CustomAuthenticationException(String msg, AuthenticationFailureType authenticationFailureType) {
        super(msg);
        this.authenticationFailureType = authenticationFailureType;
    }

    public JSONObject getErrorResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", authenticationFailureType.toString());
        jsonObject.put("error_description", this.getMessage());
        return jsonObject;
    }
}
